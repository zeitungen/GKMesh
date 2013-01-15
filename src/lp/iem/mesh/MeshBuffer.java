package lp.iem.mesh;

import java.util.ArrayList;
import java.util.List;

import lp.iem.gk.Color;
import lp.iem.gk.Normal;
import lp.iem.gk.Point;
import lp.iem.gk.Point2;
import lp.iem.gk.Vector;

public class MeshBuffer {
	private Name semantic; 	// attribut name
	private int count; 		// number of vectors
	private int size; 		// 1, 2, 3, 4, dimension vectors
	private List<Float> data = new ArrayList<Float>();	// storage attributes

	public MeshBuffer() {
		count = 0;
		size = 0;
	}

	public MeshBuffer(Name _sem, int _size) {
		semantic = _sem;
		count = 0;
		size = _size;
	}

	// ! insertion d'un attribut generique.
	public void push(Object[] attribute) {
		Object obj = attribute[0];
		if (obj instanceof Float) {
			count++;
			for (int i = 0; i < size; i++)
				data.add((Float) attribute[i]);
		}
	}

	// ! insertion d'un attribut generique.
	public void push(Object attribute) {
		if (attribute instanceof Float) {
			count++;
			for (int i = 0; i < size; i++)
				data.add((Float) attribute);
		}
	}

	// ! insertion d'un attribut generique.
	public void push(float[] attribute) {
		count++;
		for (int i = 0; i < size; i++)
			data.add(attribute[i]);
	}

	// ! insertion d'un attribut float.
	public int push(float attribute) {
		if (size != 1)
			return -1;

		// tableau permettant de stocker la valeur d'attribute pour l'envoyer a
		// push()
		float data[] = new float[1];
		data[0] = attribute;
		push(data);

		return 0;
	}

	// ! insertion d'un attribut point 2d.
	public int push(Point2 attribute) throws Exception {
		if (size != 2)
			return -1;

		// tableau permettant de stocker les attributs de l'objet point : x et y
		float data[] = new float[2];

		for (int i = 0; i < data.length; i++) {
			data[i] = attribute.get(i);
		}

		push(data);
		return 0;
	}

	// ! insertion d'un attribut point 3d.
	public int push(Point attribute) throws Exception {
		if (size != 3)
			return -1;

		// tableau permettant de stocker les attributs de l'objet point : x et y
		float data[] = new float[3];

		for (int i = 0; i < data.length; i++) {
			data[i] = attribute.get(i);
		}

		push(data);
		return 0;
	}

	// ! insertion d'un attribut Vector 3d.
	public int push(Vector attribute) throws Exception {
		if (size != 3)
			return -1;

		// tableau permettant de stocker les attributs de l'objet point : x et y
		float data[] = new float[3];

		for (int i = 0; i < data.length; i++) {
			data[i] = attribute.get(i);
		}

		push(data);
		return 0;
	}

	// ! insertion d'un attribut Normal 3d.
	public int push(Normal attribute) throws Exception {
		if (size != 3)
			return -1;

		// tableau permettant de stocker les attributs de l'objet point : x et y
		float data[] = new float[3];

		for (int i = 0; i < data.length; i++) {
			data[i] = attribute.get(i);
		}

		push(data);
		return 0;
	}

	// ! insertion d'un attribut color rgba.
	public int push(Color attribute) throws Exception {
		if (size != 4)
			return -1;

		// tableau permettant de stocker les attributs de l'objet point : x et y
		float data[] = new float[4];

		for (int i = 0; i < data.length; i++) {
			data[i] = attribute.get(i);
		}

		push(data);
		return 0;
	}

	// ! renvoie le nombre d'attributs stockes.
	public int attributeCount() {
		return count;
	}

	public Color asColor(int id) {
		if (size == 4 && id < data.size() + 1)
			return new Color(data.get(4 * id), data.get(4 * id + 1),
					data.get(4 * id + 2), data.get(4 * id + 3));

		else
			return null;
	}

	public Point asPoint(int id) {
		if (size == 3 && id < data.size() + 1)
			return new Point(data.get(3 * id), data.get(3 * id + 1),
					data.get(3 * id + 2));
		else
			return null;
	}

	public Vector asVector(int id) {
		if (size == 3 && id < data.size() + 1)
			return new Vector(data.get(3 * id), data.get(3 * id + 1),
					data.get(3 * id + 2));
		else
			return null;
	}

	public Normal asNormal(int id) {
		if (size == 3 && id < data.size() + 1)
			return new Normal(data.get(3 * id), data.get(3 * id + 1),
					data.get(3 * id + 2));
		else
			return null;
	}

	public Point2 asPoint2(int id) {
		if (size == 2 && id < data.size() + 1)
			return new Point2(data.get(2 * id), data.get(2 * id + 1));
		else
			return null;
	}

	public float asFloat(int id) {
		if (size == 1)
			return data.get(id);
		else
			return -1;
	}

	public Name getName() {
		return semantic;
	}

	public void setName(Name semantic) {
		this.semantic = semantic;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Float> getData() {
		return data;
	}

	public void setData(List<Float> data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		final MeshBuffer p = (MeshBuffer) o;
		return (this.count == p.getCount() && this.data == p.getData()
				&& this.semantic == p.getName() && this.size == p.getSize());
	}

}
