package stack is
 procedure push(x:integer);
 function pop return integer;
 stack_overflow, stack_underflow:exception;
end stack;
