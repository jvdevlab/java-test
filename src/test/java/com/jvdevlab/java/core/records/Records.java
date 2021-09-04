package com.jvdevlab.java.core.records;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Preview since Java 14
public class Records {

    @Test
    public void recordGetters() {
        record Person(String firstName, String lastName) {
        }

        Person p = new Person("V", "J");
        assertEquals("V", p.firstName());
        assertEquals("J", p.lastName());
    }

    @Test
    public void recordToString() {
        record Person(String firstName, String lastName) {
        }

        Person p = new Person("V", "J");
        assertEquals("Person[firstName=V, lastName=J]", p.toString());
    }

    @Test
    public void cannotExtendAnyOtherClass() {
        // "Records cannot extend any other class" "Syntax error on token "extends",
        // implements expected"
        // record Person (String firstName, String lastName) extends Records{}
    }

    @Test
    public void cannotDeclareInstanceFields() {
        // "cannot declare instance fields" "User declared non-static fields age are
        // not permitted in a record"
        // record Person(String firstName,String lastName){public int age;}
    }

    @Test
    public void cannotBeAbstract() {
        // "Cannot be abstract"
        // VSCode doesn't complain but we get compiler error:
        // "java.lang.ClassFormatError:
        // Illegal class modifiers in class com/vj/java/core/records/Records: 0x418"
        // abstract record Person(String firstName,String lastName){}
    }

    @Test
    public void cannotBeNonFinal() {
        // "Records are implicitly final"
        @SuppressWarnings("unused")
        record Person(String firstName, String lastName) {
        }
        // "The type John cannot subclass the final class Person"
        // class John extends Person {}
    }

    @Test
    public void cannotHaveNonFinalComponents() {
        // "The components of a record are implicitly final."
        // "The final field Person2.firstName cannot be assigned"
        // record Person2(String firstName,String lastName){public String
        // firstName(){firstName+="can't do that";return firstName;}}
    }

    @Test
    public void canImplementInterfaces() {
        // Implement interfaces
        record Person(String firstName, String lastName) implements Cloneable {
        }
        Person p = new Person("firstName", "lastName");
        assertTrue(p instanceof Cloneable);
    }

    @Test
    public void canHaveStaticMembers() {
        // "Any other fields which are declared must be static."
        @SuppressWarnings("unused")
        record Person2(String firstName, String lastName) {
            public static int age;
            static {
                age = 100;
            }

            public static int calculate() {
                return age * 2;
            }
        }
        assertEquals(100, Person2.age);
        assertEquals(200, Person2.calculate());
    }

    @Test
    public void canHavePrivateFinalFieldsThatCorrespondToComponentsOfTheState() {
        // "the private final fields which correspond to components of the state
        // description"
        // VJ: Not working. "Duplicate field Person3.firstName"
        // record Person3(String firstName,String lastName){private final String
        // firstName;}
    }

    @Test
    public void canHavePublicAccessorsForComponentsOfTheSate() {
        // "a public read accessor method, with the same name and type, for each
        // component in the state description;"
        record Person4(String firstName, String lastName) {
            public String firstName() {
                return "N/A";
            }
        }
        Person4 p4 = new Person4("firstName", "lastName");
        assertEquals("firstName", p4.firstName);
        assertEquals("N/A", p4.firstName());
    }

    @Test
    public void canHaveConstructorWithoutParametersCorrespondingToComponentsOfTheState() {
        // "Special consideration is provided for explicitly declaring the canonical
        // constructor (the one whose signature matches the record's state description).
        // The constructor may be declared without a formal parameter list (in this
        // case, it is assumed identical to the state description), and any record
        // fields which are definitely unassigned when the constructor body completes
        // normally are implicitly initialized from their corresponding formal
        // parameters (this.x = x) on exit. This allows an explicit canonical
        // constructor to perform only validation and normalization of its parameters,
        // and omit the obvious field initialization."
        // Not VSCode formatting is not working yet.
        @SuppressWarnings("unused")

        record Person5(String firstName, String lastName) {
            // no parameters and brackets
            public Person5 {
                if (firstName == null)
                    throw new NullPointerException("First Name can't be null");
                if (lastName == null)
                    throw new NullPointerException("Last Name can't be null");
            }
        }
        ;
        // This doesn't work for some reason: "java.lang.VerifyError: Constructor must
        // call super() or this() before return"
        // assertThrows(NullPointerException.class, () -> new Person5(null, null));
    }
}
