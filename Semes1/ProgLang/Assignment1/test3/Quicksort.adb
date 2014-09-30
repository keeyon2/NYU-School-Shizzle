package QuicksortPackage is
    type Int_Array is array of Integer;

    function Quicksort(Numbers : Int_Array) return Int_Array;
end Quicksort;

Package body Quicksort is
    
    function Quicksort(Numbers : Int_Array) return Int_Array is
    begin
        return Numbers;
    end
E
