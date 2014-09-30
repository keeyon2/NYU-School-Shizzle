with Ada.Text_IO; use Ada.Text_IO;

package body printingprogram is
   
   procedure Printer is 
   begin
    Put_Line ("Print");
   end Printer;

   procedure adder is
   begin
    Put_Line ("add");
   end adder;

   procedure sorter is
   begin
    Put_Line ("sort");
   end sorter;

begin
    Put_Line ("Hello, world!");
end PrintingProgram;