with Ada.Text_IO;
with Ada.Integer_Text_IO;


package body QuicksortPackage is
    use Ada.Text_IO;
    use Ada.Integer_Text_IO;

    protected QuicksortTask is 
        procedure sorting(Numbers : in out Int_Array);
    end QuicksortTask;

    protected body QuicksortTask is
        procedure sorting (Numbers : in out Int_Array) is
            midpoint : Integer;
            m : Integer;
            i : Integer;
            j : Integer;
            TempNumber : Integer;
            firstHalf : Int_Array(1..1);
            secondHalf : Int_Array(1..1);
            begin
                Put_Line("Starting with Length ");
                New_Line;
                put(Numbers'Length);
                New_Line;
                midpoint := Numbers'Length/2;
                
                if Numbers'Length = 2 then
                    Put_Line("We are at 2");
                    if Numbers'First < Numbers'Last then
                        m := Numbers'First;
                    else
                        m := Numbers'Last;
                    end if;
                end if;        
                                    


                if Numbers'Length > 2 then
                    i := 1;
                    j := Numbers'Length;
                    Put_Line("test1");
                     m := Numbers(midpoint);
                     Put_Line("test2");
                     -- Outer While
                     while  i < j
                     loop

                        -- I While
                        while Numbers(i) <= m
                        loop
                            i:= i + 1;
                        end loop;

                        --J While
                        while Numbers(j) > m
                        loop
                            j:= j - 1;
                        end loop;
                        if i < j then
                            TempNumber := Numbers(i);
                            Numbers(i) := Numbers(j);
                            Numbers(j) := TempNumber;
                        end if;
                    end loop;
                    -- Recursive Calls
                    Put_Line("Calling Recursive");
                    if midpoint = 1 and then Numbers'Length = 2 then
                        firstHalf := Numbers(1..midpoint);
                        secondHalf := Numbers(midpoint+1..Numbers'Length);
                        QuicksortTask.sorting(firstHalf);
                        QuicksortTask.sorting(secondHalf);
                    else    
                        QuicksortTask.sorting(Numbers(1..midpoint));
                        QuicksortTask.sorting(Numbers((Numbers'Length/2)+1..Numbers'Length));
                    end if;
                    return;
                else
                    Put_Line("Returning from Quicksort");    
                end if;
        end sorting;
    end QuicksortTask;

    procedure Quicksort(Numbers : in out Int_Array) is
    begin
        QuicksortTask.sorting(Numbers);
    end Quicksort;
end QuicksortPackage;
