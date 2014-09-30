with text_io;
with stack;
--test
procedure main is
  use text_io;
  package int_io is new integer_io(integer);
  use int_io;

  use stack;
  x,y,z:integer;

begin
  put("Enter number of pushes > ");
  get(x);
  put("Enter number of pops > ");
  get(y);
  for i in 1..x loop
    push(i);
  end loop;
  for i in 1..y loop
    z := pop;
    put(z);
  end loop;
exception
   when stack_overflow =>
        put("Stack has overflowed"); new_line;
   when stack_underflow =>
        put("Stack has underflowed"); new_line;
end main;