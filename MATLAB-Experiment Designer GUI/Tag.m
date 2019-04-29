 function [x, y, ID_CODE] = Tag(x, y, CreateOrCopyTag)
%create Id tags = Generate dec between -1 & + 1 and add letter code;


switch CreateOrCopyTag
    case 'copy'
        %Copies ID code for new experiment session. takes code from
        %previously tagged datasets.
        a = cell(1, 1);
        a{1, 1} = x;
        x = a;
        x{2} = y; 
        ID_CODE = y{1, 13};
    case 'create'
        %generates unique ID code
        numcode = string(randi([1, 1000],1,  4));
        letterIds = randi([1, 26], 1, 4);
        [lettercode] = AlphabetListGeni(letterIds, 'letters');
        lenletcode = length(lettercode);
        ID_CODE = strings(1, length(lenletcode));
        for i = 1:lenletcode
            ID_CODE(i) = strcat(numcode(i), convertCharsToStrings(upper(lettercode(i))));
        end 
        ID_CODE = strjoin(ID_CODE(randperm(length(ID_CODE))));

        a = cell(1, 1);
        a{1, 1} = x;
        x = a;
        [~, ywidth] = size(y);
        if ywidth == 12
            y{1, 13} = ID_CODE;
            y.Properties.VariableNames{'Var13'} = 'ID_CODE';
            x{2} = y;
        end
end 