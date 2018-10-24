# Java8 新特性

## Functional接口
@FunctionalInterface
> Conceptually, a functional interface has exactly one abstract method.
> If an interface declares an abstract method overriding one of the public methods of Object, that also does not count toward the interface's abstract method count since any implementation of the interface will have an implementation from Object or elsewhere.
> Note that instances of functional interfaces can be created with lambda expressions, method references, or constructor references.

java.util.function
Consumer
Function
Predicate
Supplier

Comparator
Runnable

## Lambda表达式
匿名类

一个lambda可以由用逗号分隔的参数列表、 –> 符号与函数体三部分表示

Lambda可能会返回一个值,返回值的类型也是由编译器推测出来的,如果lambda的函数体只有一行的话，那么没有必要显式使用return语句

## 方法引用
构造器引用 Class::new
静态方法引用 Class::static
类的方法引用 Class::method
实例的方法引用 instance::method

实现抽象方法的参数列表，必须与方法引用方法的参数列表保持一致

实例方法要通过对象来调用，方法引用对应Lambda，Lambda的第一个参数会成为调用实例方法的对象。

什么场景适合使用方法引用
 
## Optional

map 与 flatmap区别
orElse怎么用
## Stream

Stream 不是集合元素，它不是数据结构并不保存数据，它是有关算法和计算的，它更像一个高级版本的 Iterator。原始版本的 Iterator，用户只能显式地一个一个遍历元素并对其执行某些操作；高级版本的 Stream，用户只要给出需要对其包含的元素执行什么操作，Stream 会隐式地在内部进行遍历，做出相应的数据转换。

Stream 就如同一个迭代器（Iterator），单向，不可往复，数据只能遍历一次，遍历过一次后即用尽了，就好比流水从面前流过，一去不复返。

Java 8 中的 Stream 是对集合（Collection）对象功能的增强，它专注于对集合对象进行各种非常便利、高效的聚合操作（aggregate operation），或者大批量数据操作 (bulk data operation)。Stream API 借助于同样新出现的 Lambda 表达式，极大的提高编程效率和程序可读性。

流的操作类型分为两种：

Intermediate：一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
Terminal：一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。