// package com.github.nikson.dpf


class ParameterizedFactoryMethod {
	
	public static void main(String args[]) {

	}

	// Parameterized factory pattern 
	IShape createProduct(String type) {

	    if ( "R".equals(type) ) 	return new Rectangle();
	    else if ("C".equals(type)) 	return new Circle();
	}

	interface IShape {
	    int area;
	    int width;
	    int height;
	}

	class Rectangle implements IShape {
	
	}

	class Circle implements IShape {
	
	}
}
