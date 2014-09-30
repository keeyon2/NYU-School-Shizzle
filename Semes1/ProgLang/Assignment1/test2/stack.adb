package body stack is
 subtype stack_index is integer range 1..20;
 the_stack: array (stack_index) of integer;
 index: integer:= 1;
 procedure push(x:integer) is
  begin
    if (index > 20) then
      raise stack_overflow;
    else
      the_stack(index) := x;
      index := index + 1;
    end if;
  end;

 function pop return integer is
 begin
   if (index <= 1) then
     raise stack_underflow;
   else
    index := index - 1;
    return the_stack(index);
   end if;
 end;
end stack;
