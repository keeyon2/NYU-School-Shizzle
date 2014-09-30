with Ada.Text_IO;
with Ada.Integer_Text_IO;


package body QuicksortPackage is
    use Ada.Text_IO;
    use Ada.Integer_Text_IO;

    protected QuicksortTask is 
        procedure sorting(Numbers : in out Int_Array; initialIndex : Integer; finalIndex : Integer);
    end QuicksortTask;

    protected body QuicksortTask is
        procedure sorting (Numbers : in out Int_Array; initialIndex : Integer; finalIndex : Integer) is
            -- midpoint : Integer;
            midpointIndex : Integer;
            currentArrayLength: Integer;
            m : Integer;
            i : Integer;
            j : Integer;
            TempNumber : Integer;
            -- firstHalf : Int_Array(1..1);
            -- secondHalf : Int_Array(1..1);
            begin

                currentArrayLength := finalIndex - initialIndex + 1;
                if currentArrayLength < 3 then
                    midpointIndex := initialIndex;
                else
                    midpointIndex := ((currentArrayLength)/2) + initialIndex;
                end if;    
                
                m := Numbers(midpointIndex);
                i := initialIndex;
                j := finalIndex;    

                -- if Numbers'Length = 2 then
                --     Put_Line("We are at 2");
                --     if Numbers'First < Numbers'Last then
                --         m := Numbers'First;
                --     else
                --         m := Numbers'Last;
                --     end if;
                -- end if;        
                                    


                if currentArrayLength > 1 then
                    -- i := 1;
                    -- j := Numbers'Length;
                    -- Put_Line("test1");
                    --  m := Numbers(midpoint);
                    --  Put_Line("test2");
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
                    QuicksortTask.sorting(Numbers, initialIndex, midpointIndex);
                    QuicksortTask.sorting(Numbers, midpointIndex + 1, finalIndex);
                    return;
                end if;
        end sorting;
    end QuicksortTask;

    procedure Quicksort(Numbers : in out Int_Array) is
    begin
        QuicksortTask.sorting(Numbers, 1, Numbers'Length);
    end Quicksort;
end QuicksortPackage;
