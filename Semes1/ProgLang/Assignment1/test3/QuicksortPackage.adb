-- Keeyon Ebrahimi
-- kme322
-- N14193969

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
            midpointIndex : Integer;
            currentArrayLength: Integer;
            m : Integer;
            i : Integer;
            j : Integer;
            TempNumber : Integer;
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

                if currentArrayLength > 1 then
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
