import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReflectionDemo {
    public static void main(String[] args) {

///////////////////////// Get Class using reflection////////////////////////////////
        Class<?> concreteClass = ConcreteClass.class;
        try {
            concreteClass = Class.forName("ConcreteClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(concreteClass.getCanonicalName()); // prints ConcreteClass

        //for primitive types, wrapper classes and arrays
        Class<?> booleanClass = boolean.class;
        System.out.println(booleanClass.getCanonicalName()); // prints boolean

        Class<?> cDouble = Double.TYPE;
        System.out.println(cDouble.getCanonicalName()); // prints double

        Class<?> twoDStringArray = String[][].class;
        System.out.println(twoDStringArray.getCanonicalName()); // prints java.lang.String[][]


////////////////////////////// Get Super Class using reflection///////////////////////
        Class<?> superClass = null;
        try {
            superClass = Class.forName("ConcreteClass").getSuperclass();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(superClass); // prints "class BaseClass"
        System.out.println(Object.class.getSuperclass()); // prints "null"
        System.out.println(String[][].class.getSuperclass());// prints "class java.lang.Object"


//////////////////////////Get Public Member Classes///////////////////////////////////
        Class<?>[] classes = concreteClass.getClasses();
        System.out.println(Arrays.toString(classes));


/////////////////////////////Get Declared Classes////////////////////////////////////
        //getting all of the classes, interfaces, and enums that are explicitly declared in ConcreteClass
        Class<?>[] explicitClasses = new Class<?>[0];
        try {
            explicitClasses = Class.forName("ConcreteClass").getDeclaredClasses();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(explicitClasses));


////////////////////Get Declaring Class//////////////////////////////////////////////
        Class<?> innerClass = null;
        try {
            innerClass = Class.forName("ConcreteClass$ConcreteClassDefaultClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //prints ConcreteClass
        System.out.println(innerClass.getDeclaringClass().getCanonicalName());
        System.out.println(innerClass.getEnclosingClass().getCanonicalName());


//////////////////////////Getting Class Modifiers////////////////////////////////////
        System.out.println(Modifier.toString(concreteClass.getModifiers())); //prints "public"

        //prints "public abstract interface"
        try {
            System.out.println(Modifier.toString(Class.forName("BaseInterface").getModifiers()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//////////////////////////Get Type Parameters//////////////////////////////////////
        //Get Type parameters (generics)
        TypeVariable<?>[] typeParameters = new TypeVariable<?>[0];
        try {
            typeParameters = Class.forName("java.util.HashMap").getTypeParameters();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (TypeVariable<?> t : typeParameters)
            System.out.print(t.getName() + ",");


/////////////////////////////////Get Implemented Interfaces////////////////////
        Type[] interfaces = new Type[0];
        try {
            interfaces = Class.forName("java.util.HashMap").getGenericInterfaces();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //prints "[java.util.Map<K, V>, interface java.lang.Cloneable, interface java.io.Serializable]"
        System.out.println(Arrays.toString(interfaces));

        //prints "[interface java.util.Map, interface java.lang.Cloneable, interface java.io.Serializable]"
        try {
            System.out.println(Arrays.toString(Class.forName("java.util.HashMap").getInterfaces()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

///////////////////////////////Get All Public Methods////////////////////////
        Method[] publicMethods = new Method[0];
        try {
            publicMethods = Class.forName("ConcreteClass").getMethods();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //prints public methods of ConcreteClass, BaseClass, Object
        System.out.println(Arrays.toString(publicMethods));

//////////////////////////Get All public constructors//////////////////////
        Constructor<?>[] publicConstructors = new Constructor<?>[0];
        try {
            publicConstructors = Class.forName("ConcreteClass").getConstructors();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //prints public constructors of ConcreteClass
        System.out.println(Arrays.toString(publicConstructors));

/////////////////////Get All public fields///////////////////////////////
        Field[] publicFields = new Field[0];
        try {
            publicFields = Class.forName("ConcreteClass").getFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //prints public fields of ConcreteClass, it's superclass and super interfaces
        System.out.println(Arrays.toString(publicFields));


/////////////////////////Get All Annotations//////////////////////////////
        java.lang.annotation.Annotation[] annotations = new java.lang.annotation.Annotation[0];
        try {
            annotations = Class.forName("ConcreteClass").getAnnotations();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //prints [@java.lang.Deprecated()]
        System.out.println(Arrays.toString(annotations));

//////////////////////Get Public Field/////////////////////////////////////
        try {
            Field field = Class.forName("ConcreteClass").getField("interfaceInt");
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }

//////////////////Field Declaring Class/////////////////////////////
        try {
            Field field = Class.forName("ConcreteClass").getField("interfaceInt");
            Class<?> fieldClass = field.getDeclaringClass();
            System.out.println(fieldClass.getCanonicalName()); //prints BaseInterface
        } catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
        }


//////////////////////Get Field Type////////////////////////////////
        Field field = null;
        try {
            field = Class.forName("ConcreteClass").getField("publicInt");
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class<?> fieldType = field.getType();
        System.out.println(fieldType.getCanonicalName()); //prints int


//////////////////Get/Set Public Field Value/////////////////////////////////
        Field field2 = null;
        try {
            field2 = Class.forName("ConcreteClass").getField("publicInt");
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ConcreteClass obj = new ConcreteClass(5);
        try {
            System.out.println(field2.get(obj)); //prints 5
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            field2.setInt(obj, 10); //setting field value to 10 in object
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(field2.get(obj)); //prints 10
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


/////////////////////Get/Set Private Field Value//////////////////////
        Field privateField = null;
        try {
            privateField = Class.forName("ConcreteClass").getDeclaredField("privateString");
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //turning off access check with below method call
        privateField.setAccessible(true);
        ConcreteClass objTest = new ConcreteClass(1);
        try {
            System.out.println(privateField.get(objTest)); // prints "private string"
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            privateField.set(objTest, "private string updated");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(privateField.get(objTest)); //prints "private string updated"
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


///////////////////////Get Public Method//////////////////////////////////
        Method method = null;
        try {
            method = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //get method parameter types, prints "[class java.lang.Object, class java.lang.Object]"
        System.out.println(Arrays.toString(method.getParameterTypes()));

        //get method return type, return "class java.lang.Object", class reference for void
        System.out.println(method.getReturnType());

        //get method modifiers
        System.out.println(Modifier.toString(method.getModifiers())); //prints "public"


/////////////////////////Invoking Public Method/////////////////////////////////////////
        Method method2 = null;
        try {
            method2 = Class.forName("java.util.HashMap").getMethod("put", Object.class, Object.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, String> hm = new HashMap<>();
        try {
            method2.invoke(hm, "key", "value");
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(hm); // prints {key=value}


/////////////////////invoking private method///////////////////////////////////////////////
        Method method3 = null;
        try {
            method3 = Class.forName("BaseClass").getDeclaredMethod("method3", null);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        method3.setAccessible(true);
        try {
            method3.invoke(null, null); //prints "Method3"
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


//////////////////////////Get Public Constructor//////////////////////////////////
        Constructor<?> constructor = null;
        try {
            constructor = Class.forName("ConcreteClass").getConstructor(int.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //getting constructor parameters
        System.out.println(Arrays.toString(constructor.getParameterTypes())); // prints "[int]"

        Constructor<?> hashMapConstructor = null;
        try {
            hashMapConstructor = Class.forName("java.util.HashMap").getConstructor(null);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(hashMapConstructor.getParameterTypes())); // prints "[]"


/////////////////////////Instantiate Object using Constructor///////////////////////////////
        Constructor<?> constructor2 = null;
        try {
            constructor2 = Class.forName("ConcreteClass").getConstructor(int.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //getting constructor parameters
        System.out.println(Arrays.toString(constructor2.getParameterTypes())); // prints "[int]"

        Object myObj = null;
        try {
            myObj = constructor2.newInstance(10);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Method myObjMethod = null;
        try {
            myObjMethod = myObj.getClass().getMethod("method1", null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            myObjMethod.invoke(myObj, null); //prints "Method1 impl."
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        Constructor<?> hashMapConstructor2 = null;
        try {
            hashMapConstructor2 = Class.forName("java.util.HashMap").getConstructor(null);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(hashMapConstructor2.getParameterTypes())); // prints "[]"
        try {
            HashMap<String, String> myMap = (HashMap<String, String>) hashMapConstructor2.newInstance(null);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}