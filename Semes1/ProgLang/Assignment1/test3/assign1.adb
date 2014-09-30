with Ada.Text_IO;
with Ada.Integer_Text_IO;
with Ada.Strings.Unbounded;
with QuicksortPackage;


procedure assign1 is
    use Ada.Text_IO;
    use Ada.Integer_Text_IO;
    use Ada.Strings.Unbounded;
    use QuicksortPackage;

    -- Create used variables
    numbersize:integer := 30;
    InputNumbers : Int_Array(1..numbersize);
    X : Integer;


    task type Printer is
        entry Start(Numbers : Int_Array);
        entry Sorted(Numbers : Int_Array);
        entry SumAdded(Sum : Integer);
    end Printer; 

    task type add is
        entry Sorted(Numbers : Int_Array);
    end add;

    task type sort is
        entry Printed(Numbers : Int_Array);
    end sort;
   
    -- Declare Tasks
    printTask : Printer;
    addTask : add;
    sortTask : sort;

    task body Printer is
    PrintArray : Int_Array(1..30);
    begin
        accept Start(Numbers : Int_Array) do
            -- Print Numbers
            for Count in 1 .. numbersize
            loop
                put(Numbers(Count));
            end loop;
            New_Line;
            New_Line;
            PrintArray := Numbers;
        end Start;
        
        -- Notify Sorter
        sortTask.Printed(PrintArray);

        -- Received Sorted Numbers
        accept Sorted(Numbers : Int_Array) do
            -- Print Numbers
            for Count in 1 .. numbersize
            loop
                put(Numbers(Count));
            end loop;
            New_Line;
            New_Line;
        end Sorted;

        -- Received Sum
        accept SumAdded(Sum : Integer) do
            put(Sum);
        end SumAdded;
    end Printer;

    task body add is
    Sum : Integer;
    begin   
        Sum := 0;     
        accept Sorted(Numbers : Int_Array) do
            for Count in 1 .. numbersize
            loop
                Sum := Sum + InputNumbers(Count);
            end loop;
        end Sorted;
        printTask.SumAdded(Sum);
    end add;

    task body sort is
    SortArray : Int_Array(1..30);
    begin
        accept Printed(Numbers : Int_Array) do
            SortArray := Numbers;
            Quicksort(SortArray);
            
        end Printed;
        
        printTask.Sorted(SortArray);
        addTask.Sorted(SortArray);

    end sort;


begin
    -- Receive Numbers
    for Count in 1 .. numbersize
    loop
        Get(X);
        InputNumbers(Count) := X;
    end loop;
    printTask.Start(InputNumbers);
end assign1;
