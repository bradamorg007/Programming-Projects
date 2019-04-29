function [Incorrect_Probe_Data] = IncorrectProbeDataGeni(SampleVector, DataType, Range) 

switch DataType
                    
    case "Integers"
        Incorrect_Probe_Data = IntProbeGeni(SampleVector,Range);
    case "Letters"
        Incorrect_Probe_Data = LetterProbeGeni(SampleVector);
    case "Mixed"
        
        %Seperate Sample vector into Letters and Numbers 
        [LetterVector, NumberVector] = MixedVectorSplitter(SampleVector);
        
        %If user states mixed but generates only numbers or letters then
        %dont excute both INT and LETTER probeGENIs.
        if isnumeric(LetterVector) == 1 && LetterVector == 0
            Incorrect_Probe_Data = IntProbeGeni(NumberVector,Range);
        elseif isnumeric(NumberVector) == 1 && NumberVector == 0
            Incorrect_Probe_Data = LetterProbeGeni(LetterVector);
        else 
            if rand < 0.5 %should I make this Variable? Probability of the probe being an incorrect INT or LETTER
                Incorrect_Probe_Data = IntProbeGeni(NumberVector,Range);
            else
                Incorrect_Probe_Data = LetterProbeGeni(LetterVector);
            end 
        end 
end 
        
        
        %convert Sample vector to numbers 
        
        %The nested functions will extend the users number range, and
        %sample a number from this extended numberline whilst excluding
        %numbers in the Sample Vector - thus creating an incorrect probe
        
        function [probe_element] = IntProbeGeni(SampleVector,Range)
        
            SampleVector = str2double(SampleVector);
            probe_element = strings(1,1);
            
            
            numberline = setdiff((Range(1)- 30):(Range(2)+ 30), SampleVector);
            SampleSelection = randi([1, length(numberline)], 1);
            probe_element(1,1) = numberline(SampleSelection);
        
            for i = 1:length(SampleVector)
            
                if isequal(probe_element, SampleVector(i)) == 1
                    msg = 'ERROR: The System has Produced a correct/matching probe element when in fact the probeSequence should be an incorrect/non-matching probe element';
                    msgbox(msg);
                    error(msg);
                end 
            end
        end 
%=======================================================================================================================================================================
%=======================================================================================================================================================================
    
       function [probe_element] = LetterProbeGeni(SampleVector)     
            %Convert to Sample vector from String to Charcter
            %Find the letter positions of Sample vector made up of letters
            %their corrisponding numbers can then be excluded from the sampling
            %range
            ExclusionVect = AlphabetListGeni(SampleVector, "numerical"); 

            numberline = setdiff(1:26, ExclusionVect);
            probe_NumericForm = randi([1, length(numberline)], 1);
            Sample_Selection = numberline(probe_NumericForm);
            Probe_CharForm = AlphabetListGeni(Sample_Selection, "letters");
            probe_element = convertCharsToStrings(Probe_CharForm);

            for i = 1:length(SampleVector)

                if strcmp(probe_element, SampleVector(i)) == 1            
                    msg = 'ERROR: The System has Produced a correct/matching probe element when in fact the probeSequence should be an incorrect/non-matching probe element';
                    msgbox(msg);
                    error(msg); 
                end 
            end
       end 
%=======================================================================================================================================================================
%=======================================================================================================================================================================
   
                          
end 