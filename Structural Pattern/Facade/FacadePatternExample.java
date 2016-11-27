// package com.nikson.github.dpf

/*
	Facade:  provide a unified interface to a set of interfaces in subsystem. 
	Encapsulate all in a higher level interface.
*/


class FacadePatternExample {

	public static void main(String args[]) {

		FacadePatternExample.CompilerFacade cf = new FacadePatternExample().new CompilerFacade();
		cf.compile();

	}

	class CompilerFacade {
		private Scanner s;
		private Parser p;
		private NodeBuilder nb;
		private CodeGenerator cg;

		CompilerFacade() {
			// ToDo: initiate all interface 
			// s = new ScannerImpl();
		}

		public void compile() {
			// Call subsystem interfaces
			// data = s.read(input)
			// format_data = p.parse(data)
			// NodeTree nt = nb.traverse(format_data)
			// code = cg.generate(nt);

			System.out.println("Compiler called...");
		}
	}

	// Subsystem interfaces 
	interface Scanner { }
	interface Parser { }
	interface NodeBuilder { }
	interface CodeGenerator { } 

	// ToDo: implementation of subsystem interfaces 
}
