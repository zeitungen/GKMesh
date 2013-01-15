package lp.iem.mesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lp.iem.gk.BBox;
import lp.iem.gk.Geometry;
import lp.iem.gk.Normal;
import lp.iem.gk.PNTriangle;
import lp.iem.gk.Point;
import lp.iem.gk.Point2;
import lp.iem.gk.Triangle;
import lp.iem.gk.Vector;

import lp.iem.io.IOResource;

/**
 * Representation of a triangulated mesh. A mesh is a set of triangles. Triangle
 * is a triplet of indices <abc> to find the position normal and texture
 * coordinates for each vertex a, b, c. A triangle (see MeshMaterial) was also
 * associated one matter. Triangles sharers the same material can be grouped in
 * SubMesh for an effective display.
 * 
 * @author Maxence Trinqet & Maxime Journaux
 * 
 */
public class Mesh extends IOResource {
	private List<Point> m_positions = new ArrayList<Point>();
	private List<Normal> m_normals = new ArrayList<Normal>();
	private List<Point2> m_texcoords = new ArrayList<Point2>();
	private List<Integer> m_indices = new ArrayList<Integer>(); 			// 3*triangles.size()
	private List<Integer> m_materials_id = new ArrayList<Integer>(); 		// triangles.size()
	private List<Integer> m_smooth_groups = new ArrayList<Integer>(); 		// triangles.size()
	private List<Integer> m_position_adjacency = new ArrayList<Integer>(); 	// positions.size(), first element of the adjency vertex list
	private List<Integer> m_adjacency = new ArrayList<Integer>(); 			// 2*positions.size() ? global list m_adjacency[m_position_adjacency[id]] .. -1
	private List<SubMesh> m_submeshes = new ArrayList<SubMesh>();
	private List<MeshMaterial> m_materials = new ArrayList<MeshMaterial>();
	private List<MeshBuffer> m_attributes_buffer = new ArrayList<MeshBuffer>();

	private MeshMaterial m_default_material;
	private BBox m_bbox = new BBox();;

	public Mesh() {
		super();
		m_default_material = new MeshMaterial("default");
	}

	/**
	 * declare a set of attributes point 2d.
	 * 
	 * @param semantic
	 * @param attribute_tag
	 * @return
	 */
	public MeshBuffer attachAttributeBuffer(Name semantic, Object attribute_tag) {
		MeshBuffer buffer = findBuffer(semantic);
		if (buffer != null)
			return null; // deja attache

		buffer = new MeshBuffer(semantic, 2);
		m_attributes_buffer.add(buffer);
		return buffer;
	}

	public Point position(int id) {
		if (id >= 0 && id < m_positions.size())
			return m_positions.get(id);
		else
			return null;
	}

	/**
	 * returns the normal of a vertex.
	 * 
	 * @param id
	 * @return
	 */
	public Normal normal(int id) {
		if (id >= 0 && id < m_normals.size())
			return m_normals.get(id);
		else
			return null;
	}

	/**
	 * returns the coordinate of a vertex texture.
	 * 
	 * @param id
	 * @return
	 */
	public Point2 texcoords(int id) {
		if (id >= 0 && id < m_texcoords.size())
			return m_texcoords.get(id);
		else
			return null;
	}

	/**
	 * add a vertex
	 * 
	 * @param point
	 * @return
	 */
	public int pushPosition(Point point) {
		m_positions.add(point);
		m_bbox.union(point);
		return m_positions.size() - 1;
	}

	/**
	 * add a set of vertices.
	 * 
	 * @param positions
	 */
	public void attachPositionBuffer(ArrayList<Point> positions) {
		m_positions = positions;
	}

	/**
	 * adds a set of vertices.
	 * 
	 * @param n
	 * @param positions
	 */
	public void attachPositionBuffer(int n, ArrayList<Point> positions) {
		if (n < positions.size() + 1) {
			ArrayList<Point> listPoint = new ArrayList<Point>();
			for (int i = 0; i < n; i++)
				listPoint.add(positions.get(i));
			m_positions = listPoint;
		}
	}

	/**
	 * returns the number of vertices of the mesh
	 * 
	 * @return
	 */
	public int positionCount() {
		return (int) m_positions.size();
	}

	/**
	 * add a normal
	 * 
	 * @param normal
	 * @return
	 */
	public int pushNormal(Normal normal) {
		m_normals.add(normal);
		return m_normals.size() - 1;
	}

	/**
	 * add a set of normal
	 * @param normals
	 */
	public void attachNormalBuffer(ArrayList<Normal> normals) {
		m_normals = normals;
	}

	/**
	 * add a set of normal
	 * @param n
	 * @param normals
	 */
	public void attachNormalBuffer(int n, ArrayList<Normal> normals) {
		if (n < normals.size() + 1) {
			ArrayList<Normal> listNormal = new ArrayList<Normal>();
			for (int i = 0; i < n; i++) {
				listNormal.add(normals.get(i));
			}
			m_normals = listNormal;
		}
	}

	/**
	 * 
	 * @return the number of normal mesh.
	 */
	public int normalCount() {
		return (int) m_normals.size();
	}

	/**
	 * add a coordinated texture.
	 * @param texcoord
	 * @return
	 */
	public int pushTexcoord(Point2 texcoord) {
		m_texcoords.add(texcoord);
		return m_texcoords.size() - 1;
	}

	/**
	 * adds a set of texture coordinates
	 * @param texcoords
	 */
	public void attachTexcoordBuffer(ArrayList<Point2> texcoords) {
		m_texcoords = texcoords;
	}

	// ! ajoute un ensemble de coordonnees de texture.
	public void attachTexcoordBuffer(int n, ArrayList<Point2> texcoords) {
		if (n < texcoords.size() + 1) {
			ArrayList<Point2> listPoint2 = new ArrayList<Point2>();
			for (int i = 0; i < n; i++) {
				listPoint2.add(texcoords.get(i));
			}
			m_texcoords = listPoint2;
		}

	}

	// ! renvoie le nombre de coordonnees de textures des sommets du maillage.
	public int texcoordCount() {
		return (int) m_texcoords.size();
	}

	public int attachAttributeBuffer(Name semantic, ArrayList<Object> attributes) {
		MeshBuffer buffer = attachAttributeBuffer(semantic, new Object());

		if (buffer == null)
			return -1;

		int n = (int) attributes.size();
		for (int i = 0; i < n; i++)
			buffer.push(attributes.get(i));
		return 0;
	}

	public int attachAttributeBuffer(Name semantic,
			ArrayList<Object> attributes, int n) {
		MeshBuffer buffer = attachAttributeBuffer(semantic, new Object());

		if (buffer == null)
			return -1;

		for (int i = 0; i < n; i++)
			buffer.push(attributes.get(i));
		return 0;
	}

	// ! renvoie un buffer d'apres son nom / semantique.
	public MeshBuffer findBuffer(Name semantic) {
		int n = (int) m_attributes_buffer.size();
		for (int i = 0; i < n; i++)
			if (m_attributes_buffer.get(i).getName() == semantic)
				return m_attributes_buffer.get(i);
		return null;
	}

	// ! renvoie le nombre de buffers d'attributs.
	public int bufferCount() {
		return (int) m_attributes_buffer.size();
	}

	// ! renvoie un buffer.
	public MeshBuffer buffer(int id) {
		if (id < m_attributes_buffer.size() && id > -1)
			return m_attributes_buffer.get(id);
		else
			return null;
	}

	// ! insere un attribut dans l'ensemble associe.

	void pushAttribute(Name semantic, Object attribute) {
		MeshBuffer buffer = findBuffer(semantic);
		if (buffer == null)
			return;
		buffer.push(attribute);
	}

	// ! ajoute un triangle
	public void pushTriangle(int a, int b, int c, int material_id,
			int smooth_group) {
		m_indices.add(a);
		m_indices.add(b);
		m_indices.add(c);

		m_materials_id.add(material_id);
		m_smooth_groups.add(smooth_group);
	}

	// ! ajoute un triangle
	public void pushTriangle(int a, int b, int c, int material_id) {
		m_indices.add(a);
		m_indices.add(b);
		m_indices.add(c);

		m_materials_id.add(material_id);
		m_smooth_groups.add(-1);
	}

	// ! renvoie le nombre de triangles du maillage.
	public int triangleCount() {
		return (int) m_indices.size() / 3;
	}

	// ! renvoie le nombre d'indices du maillage.
	public int indiceCount() {
		return (int) m_indices.size();
	}

	// ! ajoute un submesh.
	public void pushSubMesh(int begin, int end, int material_id) {
		m_submeshes.add(new SubMesh(begin, end, material_id));
	}

	// ! renvoie le nombre de submesh.
	public int subMeshCount() {
		return (int) m_submeshes.size();
	}

	// ! renvoie un submesh.
	public SubMesh subMesh(int submesh_id) {
		if (submesh_id < m_submeshes.size())
			return m_submeshes.get(submesh_id);
		else
			return null;
	}

	// ! renvoie la matiere d'un submesh.
	public MeshMaterial subMeshMaterial(int id) {
		int material_id = m_submeshes.get(id).getMaterial_id();

		if (material_id < 0)
			return m_default_material;
		else
			return m_materials.get(material_id);
	}

	// ! definit la matiere par defaut.
	public int pushDefaultMaterial() {
		m_materials.add(m_default_material);

		return (int) m_materials.size() - 1;
	}

	// ! insere une matiere.
	public int pushMaterial(MeshMaterial material) {
		m_materials.add(material);
		return (int) m_materials.size() - 1;
	}

	// ! remplace l'ensemble de matieres.
	public void attachMaterials(ArrayList<MeshMaterial> materials) {
		m_materials = materials;
	}

	// ! renvoie le nombre de matieres.
	public int materialCount() {
		return (int) m_materials.size();
	}

	// ! renvoie une matiere.
	public MeshMaterial material(int material_id) {
		if (m_materials.isEmpty())
			return m_default_material;
		else
			return m_materials.get(material_id);
	}

	// ! renvoie la matiere d'un triangle.
	public MeshMaterial triangleMaterial(int id) {
		int material_id = m_materials_id.get(id);

		if (material_id < 0)
			return m_default_material;
		else
			return material(material_id);
	}

	// ! renvoie l'indice de la matiere d'un triangle.
	public int getTriangleMaterialId(int id) {
		return m_materials_id.get(id);
	}

	// ! renvoie un triangle 'indexe', les 3 indices des sommets du triangle.
	public MeshTriangle getMeshTriangle(int id) {
		if (id >= 0 && id < triangleCount())
			return new MeshTriangle(this, m_indices.get(3 * id),
					m_indices.get(3 * id + 1), m_indices.get(3 * id + 2));
		else
			return null;
	}

	// ! renvoie un triangle 'geometrique' pour le calcul d'intersection avec un
	// rayon.
	// ! utiliser les resultats de l'intersection pour calculer la normale et
	// les texcoords interpolees au point d'intersection.
	// ! cf. Mesh::getUVPoint(), Mesh::getUVNormal(), Mesh::getUVTexcoord().
	public Triangle getTriangle(int id) {
		Point a = position(m_indices.get(3 * id));
		Point b = position(m_indices.get(3 * id + 1));
		Point c = position(m_indices.get(3 * id + 2));

		return new Triangle(a, b, c);
	}

	// ! renvoie un pn triangle.
	public PNTriangle getPNTriangle(int id) throws Exception {
		if (m_normals.size() != m_positions.size()) {
			// pas de normales associees aux sommets, calcule la normale
			// geometrique.
			Point a = position(m_indices.get(3 * id));
			Point b = position(m_indices.get(3 * id + 1));
			Point c = position(m_indices.get(3 * id + 2));
			Vector ab = new Vector(a, b);
			Vector ac = new Vector(a, c);
			Normal nn = new Normal(Geometry.normalize(Geometry.cross(ab, ac)));

			return new PNTriangle(getTriangle(id), nn, nn, nn);
		}

		// renvoie les normales associees aux sommets du triangle
		Normal na = normal(m_indices.get(3 * id));
		Normal nb = normal(m_indices.get(3 * id + 1));
		Normal nc = normal(m_indices.get(3 * id + 2));

		return new PNTriangle(getTriangle(id), na, nb, nc);
	}

	// ! renvoie la boite englobante d'un triangle.
	public BBox getTriangleBBox(int id) {
		Point a = position(m_indices.get(3 * id));
		Point b = position(m_indices.get(3 * id + 1));
		Point c = position(m_indices.get(3 * id + 2));

		BBox bbox = new BBox();
		bbox.union(a);
		bbox.union(b);
		bbox.union(c);

		return bbox;
	}

	// ! renvoie l'aire d'un triangle.
	public float getTriangleArea(int id) {
		Point a = position(m_indices.get(3 * id));
		Point b = position(m_indices.get(3 * id + 1));
		Point c = position(m_indices.get(3 * id + 2));
		Vector ab = new Vector(a, b);
		Vector ac = new Vector(a, c);

		return Geometry.round(.5f * Geometry.cross(ab, ac).length());
	}

	// ! calcule et renvoie la normale geometrique d'un triangle.
	public Normal getTriangleNormal(int id) throws Exception {
		Point a = position(m_indices.get(3 * id));
		Point b = position(m_indices.get(3 * id + 1));
		Point c = position(m_indices.get(3 * id + 2));
		Vector ab = new Vector(a, b);
		Vector ac = new Vector(a, c);

		Vector n = Geometry.cross(Geometry.normalize(ab),
				Geometry.normalize(ac));

		return new Normal(Geometry.normalize(n));
	}

	// ! interpole une position a l'interieur d'un triangle, connaissant les
	// coordonnees barycentriques du point.
	// ! convention p(u, v)= (1 - u - v) * a + u * b + v * c
	public Point getUVPoint(int id, float u, float v) {
		Point a = position(m_indices.get(3 * id));
		Point b = position(m_indices.get(3 * id + 1));
		Point c = position(m_indices.get(3 * id + 2));

		float w = 1.f - u - v;
		Point p1 = a.productFloat(w);
		Point p2 = b.productFloat(u);
		Point p3 = c.productFloat(v);

		p1.additionPoint(p2);
		p1.additionPoint(p3);

		return p1;
	}

	// ! interpole une normale a l'interieur d'un triangle, connaissant ses
	// coordonnees barycentriques.
	// ! convention n(u, v)= (1 - u - v) * a + u * b + v * c
	public Normal getUVNormal(int id, float u, float v) throws Exception {
		if (m_normals.isEmpty())
			// renvoie la normale geometrique, si les normales des sommets
			// n'existent pas
			return getTriangleNormal(id);

		Normal a = normal(m_indices.get(3 * id));
		Normal b = normal(m_indices.get(3 * id + 1));
		Normal c = normal(m_indices.get(3 * id + 2));

		float w = 1.f - u - v;
		Normal n1 = a.productFloat(w);
		Normal n2 = b.productFloat(u);
		Normal n3 = c.productFloat(v);

		n1.additionNormal2(n2);
		n1.additionNormal2(n3);

		return Geometry.normalize(n1);
	}

	// ! interpole une coordonnee de texture a l'interieur du triangle,
	// connaissant ses coordonnees barycentriques.
	// ! convention t(u, v)= (1 - u - v) * a + u * b + v * c
	public Point2 getUVTexcoord(int id, float u, float v) {
		if (m_texcoords.isEmpty())
			// pas de coordonnee de textures dans le maillage.
			return new Point2();

		Point2 a = texcoords(m_indices.get(3 * id));
		Point2 b = texcoords(m_indices.get(3 * id + 1));
		Point2 c = texcoords(m_indices.get(3 * id + 2));

		float w = 1.f - u - v;

		Point2 p1 = a.productPoint2(w);
		Point2 p2 = b.productPoint2(u);
		Point2 p3 = c.productPoint2(v);

		p1.add(p2);
		p1.add(p3);

		return p1;
	}

	// ! construit la liste d'adjacence des sommets (liste de triangles).
	// ! \todo acces a l'adjacence.
	public int buildAdjacency() {
		/*
		 * la tete de la liste d'adjacence du sommet d'indice a est
		 * m_position_adjacency[a] toutes les listes sont concatenees dans une
		 * liste 'globale' m_adjacency
		 * 
		 * la premiere face incidente au sommet a est m_adjacency[
		 * m_position_adjacency[a] ] les autres faces incidentes se trouvent
		 * dans les elements suivants de m_adjacency, la fin de la liste est
		 * marquee par un numero de face = -1
		 * 
		 * exemple de parcours des faces adjacentes du sommet d'indice a:
		 * for(int i= m_position_adjacency[a]; m_adjacency[i] != -1; i++; { le
		 * triangle d'indice m_adjacency[i] est adjacent / incident au sommet a
		 * }
		 */

		int triangles_n = triangleCount();
		int positions_n = positionCount();

		// initialise la liste d'adjacence des sommets
		m_position_adjacency.clear();

		// passe 1 : compte le nombre de faces partageant chaque sommet
		{
			for (int i = 0; i < triangles_n; i++) {
				int a = m_indices.get(3 * i);
				m_position_adjacency.set(a, m_position_adjacency.get(a) + 1);
				// m_position_adjacency[a]++;

				int b = m_indices.get(3 * i + 1);
				m_position_adjacency.set(b, m_position_adjacency.get(b) + 1);
				// m_position_adjacency[b]++;

				int c = m_indices.get(3 * i + 2);
				m_position_adjacency.set(c, m_position_adjacency.get(c) + 1);
				// m_position_adjacency[c]++;
			}
		}

		// passe 2 : distribue le premier element de la liste d'adjacence dans
		// la liste globale
		{
			int head = 0;
			int next = 0;
			for (int i = 0; i < positions_n; i++) {
				m_position_adjacency.set(i, m_position_adjacency.get(i) + 1);
				// m_position_adjacency[i]++; // reserve une place pour le
				// marqeur de fin de liste
				next = head + m_position_adjacency.get(i);
				m_position_adjacency.set(i, head);
				// m_position_adjacency[i]= head;

				head = next;
			}

			// alloue la liste globale d'adjacence
			m_adjacency.clear();
		}

		// passe 3 : construit la liste d'adjacence
		{
			ArrayList<Integer> last = new ArrayList<Integer>();
			// last.resize(positions_n);
			{
				// initialise la liste d'adjacence de chaque sommet
				for (int i = 0; i < positions_n; i++) {
					/*
					 * last[i]= m_position_adjacency[i]; m_adjacency[last[i]]=
					 * -1;
					 */
					last.set(i, m_position_adjacency.get(i));
					m_adjacency.set(last.get(i), -1);
				}
			}

			// insere chaque triangle dans la liste d'adjacence de ses sommets
			for (int i = 0; i < triangles_n; i++) {
				int a = m_indices.get(3 * i);
				m_adjacency.set(last.get(a) + 1, i);
				m_adjacency.set(last.get(a), -1);
				/*
				 * m_adjacency[last[a]++]= i; m_adjacency[last[a]]= -1;
				 */// termine la liste

				int b = m_indices.get(3 * i + 1);
				m_adjacency.set(last.get(b) + 1, i);
				m_adjacency.set(last.get(b), -1); // termine la liste

				int c = m_indices.get(3 * i + 2);
				m_adjacency.set(last.get(c) + 1, i);
				m_adjacency.set(last.get(c), -1); // termine la liste
			}
		}

		return 0;
	}

	/**
	 * built normals of the mesh.
	 * @return
	 * @throws Exception
	 */
	public int buildNormals() throws Exception {

		// initializes vertex normals
		m_normals.clear();

		// Step 1: normal accumulates triangles on each vertex of each triangle
		int n = triangleCount();
		for (int i = 0; i < n; i++) {
			// accumulates normal geometry of the triangle normals on 3 vertices
			int ai = m_indices.get(3 * i);
			int bi = m_indices.get(3 * i + 1);
			int ci = m_indices.get(3 * i + 2);

			Point a = position(ai);
			Point b = position(bi);
			Point c = position(ci);
			Vector ab = new Vector(a, b);
			Vector ac = new Vector(a, c);
			Normal normal = new Normal(Geometry.cross(ab, ac));

			float length = normal.length();
			float area = .5f * length;
			// normal weighted by the area of ​​the triangle and normalized
			float w = area;

			m_normals.set(ai, m_normals.get(ai).additionNormal(normal.productFloat(w)));
			m_normals.set(bi, m_normals.get(bi).additionNormal(normal.productFloat(w)));
			m_normals.set(ci, m_normals.get(ci).additionNormal(normal.productFloat(w)));
			/*
			 * m_normals[ai]+= normal * w; m_normals[bi]+= normal * w;
			 * m_normals[ci]+= normal * w;
			 */
		}
		// Step 2: normalize vertex normals
		n = (int) m_normals.size();
		for (int i = 0; i < n; i++)
			m_normals.set(i, Geometry.normalize(m_normals.get(i)));
		// m_normals[i]= Normalize(m_normals[i]);

		return 0;
	}

	/**
	 * 
	 * @param id
	 * @return the triangle smooth group
	 */
	public int getTriangleSmoothGroup(int id) {
		if (m_smooth_groups.isEmpty())
			return -1;
		return m_smooth_groups.get(id);
	}

	public int buildSubMeshes(ArrayList<Integer> alt) {
		if (m_materials_id.size() == triangleCount()) {
			List<Integer> map = m_materials_id;
			if (alt != null && alt.size() == m_materials_id.size())
				map = alt;

			// construit le tableau de permutation
			int count = triangleCount();
			ArrayList<Integer> triangles = new ArrayList<Integer>();
			for (int i = 0; i < count; i++)
				triangles.add(i);

			// tri les triangles par matiere et le tableau de permutation
			if (map != null) {
				MaterialLess less = new MaterialLess(map);
				Collections.sort(less.getMap());
			}

			// construit les sub meshes
			m_submeshes.clear();
			int begin = 0;
			int material_id = map.get(triangles.get(0));
			for (int i = 1; i < count; i++) {
				if (map.get(triangles.get(i)) == material_id)
					continue;

				pushSubMesh(begin, 3 * i, material_id);
				begin = 3 * i;
				material_id = map.get(triangles.get(i));
			}

			pushSubMesh(begin, 3 * count, material_id);

			// re ordonne les indices
			{
				ArrayList<Integer> tmp = new ArrayList<Integer>();

				for (int i = 0; i < count; i++) {
					int id = triangles.get(i);
					tmp.add(m_indices.get(3 * id)); // a
					tmp.add(m_indices.get(3 * id + 1)); // b
					tmp.add(m_indices.get(3 * id + 2)); // c
				}

				m_indices = tmp;
				// m_indices.swap(tmp);
			}

			// re ordonne les indices de matieres
			{
				ArrayList<Integer> tmp = new ArrayList<Integer>();

				for (int i = 0; i < count; i++)
					tmp.add(map.get(triangles.get(i)));

				m_materials_id = tmp;
				// m_materials_id.swap(tmp);
			}
			return 1;
		}
		return 0;
	}

	public List<Point> M_positions() {
		return m_positions;
	}

	public void M_positions(ArrayList<Point> m_positions) {
		this.m_positions = m_positions;
	}

	public List<Normal> M_normals() {
		return m_normals;
	}

	public void M_normals(ArrayList<Normal> m_normals) {
		this.m_normals = m_normals;
	}

	public List<Point2> M_texcoords() {
		return m_texcoords;
	}

	public void M_texcoords(ArrayList<Point2> m_texcoords) {
		this.m_texcoords = m_texcoords;
	}

	public List<Integer> M_indices() {
		return m_indices;
	}

	public void M_indices(ArrayList<Integer> m_indices) {
		this.m_indices = m_indices;
	}

	public List<Integer> M_materials_id() {
		return m_materials_id;
	}

	public void M_materials_id(ArrayList<Integer> m_materials_id) {
		this.m_materials_id = m_materials_id;
	}

	public List<Integer> M_smooth_groups() {
		return m_smooth_groups;
	}

	public void M_smooth_groups(ArrayList<Integer> m_smooth_groups) {
		this.m_smooth_groups = m_smooth_groups;
	}

	public List<Integer> M_position_adjacency() {
		return m_position_adjacency;
	}

	public void M_position_adjacency(ArrayList<Integer> m_position_adjacency) {
		this.m_position_adjacency = m_position_adjacency;
	}

	public List<Integer> M_adjacency() {
		return m_adjacency;
	}

	public void M_adjacency(ArrayList<Integer> m_adjacency) {
		this.m_adjacency = m_adjacency;
	}

	public List<SubMesh> M_submeshes() {
		return m_submeshes;
	}

	public void M_submeshes(ArrayList<SubMesh> m_submeshes) {
		this.m_submeshes = m_submeshes;
	}

	public List<MeshMaterial> M_materials() {
		return m_materials;
	}

	public void M_materials(ArrayList<MeshMaterial> m_materials) {
		this.m_materials = m_materials;
	}

	public List<MeshBuffer> M_attributes_buffer() {
		return m_attributes_buffer;
	}

	public void M_attributes_buffer(ArrayList<MeshBuffer> m_attributes_buffer) {
		this.m_attributes_buffer = m_attributes_buffer;
	}

	public MeshMaterial M_default_material() {
		return m_default_material;
	}

	public void M_default_material(MeshMaterial m_default_material) {
		this.m_default_material = m_default_material;
	}

	public BBox getM_bbox() {
		return m_bbox;
	}

	public void setM_bbox(BBox m_bbox) {
		this.m_bbox = m_bbox;
	}

}