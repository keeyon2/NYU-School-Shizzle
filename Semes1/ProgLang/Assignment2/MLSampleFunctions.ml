Control.Print.printDepth := 100;
Control.Print.printLength := 100;

fun foo f (op >) (x, y) z =
    let fun bar a = if x > y then z else a
    in bar [1, 2, 3]
    end

fun test (one) (two) kee =
    let val x = one kee
    in two x
    end
