package com.jvdevlab.java.language.lambda.functionalinterface;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.Test;

// I think the whole point of this functionality is to:
// "The :: operator is used in method reference to separate the class or object from the method name."
// so you can take existing classes and use their methods as lambdas (pass functions around as objects)
// This was added on late in the language so it doesn't feel natural.
public class MethodReferences {

    @Test
    public void methodReferenceToInstanceMethod() {
        class ClassA {
            public boolean instanceMethod() {
                return 1 < 2;
            }
        }

        ClassA a = new ClassA();
        assertTrue(a::instanceMethod);

        // or
        BooleanSupplier bs = a::instanceMethod;
        assertTrue(bs);

        // The above is a shortcut for this lambda expression
        assertTrue(() -> a.instanceMethod());

        // Above example is silly as you can simply call the method directly
        // But in :: version via are passing method reference and it could be called at
        // some point later, not right away.
        assertTrue(a.instanceMethod());
    }

    public static boolean staticMethod() {
        return 1 < 2;
    }

    @Test
    public void methodReferenceToStaticMethod() {
        assertTrue(MethodReferences::staticMethod);

        // or
        BooleanSupplier bs = MethodReferences::staticMethod;
        assertTrue(bs);

        // The above is a shortcut for this lambda expression
        // assertTrue(s -> MethodReferences.staticMethod(s));

        // The above example is silly as you can simply call the method directly
        // But in :: version via are passing method reference and it could be called at
        // some point later, not right away.
        assertTrue(MethodReferences.staticMethod());
    }

    // For methodReferenceToInstanceMethodOfAnArbitraryObjectOfAParticularType this
    // functional interface needs to accept a reference to an object so that we have
    // something to perform an action on.
    interface ManipulatorFunctionalInterface<T> {
        boolean manipulate(T t);
    }

    // This type of reference method invocation seems to be useful in combinations
    // with generics.
    // Examples form Java (in both cases we operate on collection of objects):
    // Arrays.sort(stringArray, String::compareToIgnoreCase);
    // personList.forEach(Person::printName);
    @Test
    public void methodReferenceToInstanceMethodOfAnArbitraryObjectOfAParticularType() {

        class ManipulatorClass<T> {
            public boolean manipulate(T t, ManipulatorFunctionalInterface<T> action) {
                return action.manipulate(t);
            }
        }

        // Note how RandomClassA and RandomClassB are unrelated and do not implement any
        // common interface.
        class RandomClassA {
            public boolean instanceMethod() {
                return 1 < 2;
            }
        }

        ManipulatorClass<RandomClassA> mA = new ManipulatorClass<>();
        // Note how this is similar but so different from methodReferenceToStaticMethod
        assertTrue(mA.manipulate(new RandomClassA(), RandomClassA::instanceMethod));

        class RandomClassB {
            public boolean someOtherInstanceMethod() {
                return 1 > 2;
            }
        }

        ManipulatorClass<RandomClassB> mB = new ManipulatorClass<>();
        assertFalse(mB.manipulate(new RandomClassB(), RandomClassB::someOtherInstanceMethod));

        // The above is a shortcut for this lambda expression
        RandomClassB b = new RandomClassB();
        assertFalse(mB.manipulate(b, bb -> bb.someOtherInstanceMethod()));
    }

    interface FactoryFunctionalInterface<T> {
        T generate();
    }

    // This type of reference method invocation seems to be useful in combinations
    // with generics.
    // Examples form Java (in both cases we operate on collection of objects):
    // Stream<Person> stream = names.stream().map(Person::new);
    // Person[] people = stream.toArray(Person[]::new);
    @Test
    public void methodReferenceToConstructor() {
        class ClassFactory<T> {
            public T convert(FactoryFunctionalInterface<T> t) {
                return t.generate();
            }
        }

        // Note how RandomClassA and RandomClassB are unrelated and do not implement any
        // common interface.
        class RandomClassA {
        }

        ClassFactory<RandomClassA> factoryA = new ClassFactory<>();
        RandomClassA a = factoryA.convert(RandomClassA::new);
        assertNotNull(a);

        class RandomClassB {
        }

        ClassFactory<RandomClassB> factoryB = new ClassFactory<>();
        RandomClassB b = factoryB.convert(RandomClassB::new);
        assertNotNull(b);

        // The above is a shortcut for this lambda expression
        RandomClassB b2 = factoryB.convert(() -> new RandomClassB());
        assertNotNull(b2);
    }
}
