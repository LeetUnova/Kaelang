## This document is to define the language and its implementation in compilation to other languages

So far this project has not done well to achieve its goal of providing a universal script that compiles to different languages, and its keywords have been incredibly different between a few languages. To remedy this, this document's goal is to define the syntax of the language and to explain how it corresponds to other languages when it is not obvious.

The language will appear like most programming languages, but some things may be ignored or redundant in some or most languages to allow for interoperability. For example, the `import` keyword will not translate into anything when compiling to McFunction.

### Keywords

* `import` : Translates to importing a library or other file in most languages
    * Syntax: `(import name)`
    * When compiling to McFunction, the `import` keyword will be ignored
* `var` : Translates to creating a typeless variable, if available
    * Syntax: `(var name |value|)`
    * If unavailable, either an optional third argument can be used to state the type or the compiler can attempt to determine the type
    * For McFunction, the variable will be stored in the scoreboard `Kaelang`
* `=` : Translates to setting an existing variable to a value
    * Syntax: `(= name |value|)`
* `==` : Translates to the equality operator
    * Syntax: `(== |value 1| |value 2|)`
* `>` : Translates to the over operator
    * Syntax: `(> |value 1| |value 2|)`
* `>=` : Translates to the over or equal to operator
    * Syntax: `(>= |value 1| |value 2|)`
* `<` : Translates to the under operator
    * Syntax: `(< |value 1| |value 2|)`
* `<=` : Translates to the under or equal to operator
    * Syntax: `(<= |value 1| |value 2|))`
* `!=` : Translates to not equal to
    * Syntax: `(!= |value 1| |value 2|)`
* `class` : Translates to the creation of a class in OOP languages, or an equivalent construct in other languages
    * Syntax: `(class name [commands])`
    * In McFunction, this will be figured out
* `proc` : Translates to the creation of a function
    * Syntax: `(proc name [commands])`
* `procargs` : Translates the creation of a function with arguments
    * Syntax: `(procargs name {args} [commands])`
* `args` : Translates to arguments of a function
    * Syntax: `(args [args])`
    * Arg Syntax: `(name type)`
        * If type is excluded, the compiler will attempt to determine the type based on usage
* `if` : Performs a conditional, generally translates to if
    * Syntax: `(if [conditionals])`
        * Conditional Syntax: `(|condition| [commands])`
            * The condition of `true` can be used for an else statement
    * In McFunctino, this will translate to an `execute`

## Implementation

There should be multiple compilers in different languages. They should be implemented such that they compile based on any of these data formats. A proper compiler should be able to use any of these to compile from Kaelang to the described language.

* json
* pkl
* nbt
* xml

A proper compiler should also be able to convert these files between each other.