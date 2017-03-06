# Design Pattern and Framework  
(Sample in Cpp, Java)

**Basic design pattern (GOF pattern)**

-----------  

- Creational: FactoryMethod, AbstractFactory, Builder, Prototype, Singleton   
- Structural: Composite, Facade, Decorator, Mediator, Flyweight, Bridge, Adapter, Proxy   
- Behavioral: ChainOfResposibility, Command, Memento, State 

It can be categorized in *Variability*, *Extensibility* and *Glue* pattern.  
**Variability**:  TemplateMethod, GenericTemplateClass, Facet, Bridge, [Objectifier]  
**Extensibility**: DimentionalClassHierarchies (Bridge, TemplateClass), ParallelClassHierarchies(Visitor, Observer, star-Bridge), ObjectRecursion (Composite[1:n], Decorator[1:1]), ChainOfResposibility, Observer  
**Glue**: Adapter, Facade, Mediator  

**TemplateClass** is the basis of: Bridge, Builder, Command, Iterator, Observer, Prototype, State, Strategy, Visitor  

-----------  
**Role Object Pattern (Riehle,Zhao)**

- Role  
- RoleType  
- Role Model 
- Role Based Design
- Role Model  
- Role Model Mapping (Class-Role Model)  
- Role constraint: role-implication (a<b), role-association(a-b), role-prohibition(a|-|b)  

-----------  

**Framework Pattern (Pree T&H metapattern)**  

T&H Metapattern for framework variability and extensibility 

Associatoin/Aggregation (blackbox):  
- T--H (1-T--H)
- n-T--H: T has n-H part  

Inheritance:  
- H<T (whitebox)  

Recursion (graybox):  
- H<=T  
- n-H<=T: H has n-T part  

Unification:  
- TH: T&H are same class  
- 1-TH  
- n-TH  

ExtenstionObject, Mixin & GenVoca:  
ex: PlayerMixin<EmployeeMixin<Person>>>, mulitiple `Mixin` result the `GenVoca` pattern    

-----------  

**Layered Design Architecture**

- n-Dimentional Facet (core facet, chain-bridge)  
- n-Bridge (chain-bridge in layered)  
- Master/slave, repository connector   

-----------    

**TAM (Tools and Material)**  
Active component (tools) and passive component (materials) in the context of an environment.

-----------  
