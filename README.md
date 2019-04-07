# Functional Java Project

My experiments, try-outs and examples of taking a functional programming approach using Java.

It is dependent on features of Java 8 and later.

I am inspired by the book Hutton, G.; Programming in Haskell; 2016 2nd edition; Cambridge University Press.
I want to have simple Maybe and Either as a functor and a monad so that I can use them for my work in Java to achieve:

- avoid nulls;
- avoid throwing exceptions.

## How to run locally

From the project root folder:

- `$ mvn clean package` to build the app.
- `$ java -cp target/my-app-1.0-SNAPSHOT.jar cloud.wraith.functional-java.App` to run the app.
- This should print `hello, world` to stdout.

## How to test

- `$ mvn test` to run all unit tests from the command line.
