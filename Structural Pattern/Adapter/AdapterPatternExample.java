// package com.github.nikson.dpf

/*
	Adapter: it a bridge between two incompatible interface 
	Two types: Class Adapter, Object Adapter 
	Class adapter is not possible in java because can't extend multiple class 
*/

class AdapterPatternExample {

	public static void main(String args[]) { 

		AdapterPatternExample ape = new AdapterPatternExample();

		ape.new TextShapeAdapter(ape.new TextView()).BoundingBox();
	}

	interface Shape {
		void BoundingBox(); 
	}

	class Rectangle implements Shape {
		public void BoundingBox() {
			System.out.println("Simple bounding box....");
		}
	}

	class TextView {
		public void GetExtent() {
			System.out.println("TextView extent bound...");
		}
	}

	// adapter 
	class TextShapeAdapter implements Shape {
	
		// adaptee 
		private TextView tv;

		public TextShapeAdapter(TextView t) {
			this.tv = t;
		}

		public void BoundingBox() {
			// TextBox is not Shape, but acting like shape 
			System.out.println("Calling adaptee method from adapter...");
			tv.GetExtent();
		}
	}
}
