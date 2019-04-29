function [data_storage] = DataSorter1_1(experiment_session)
%     clearvars -except experiment_session
ItemsPerMem = double(experiment_session{1, 2}{1, 2});
LenItems = length(ItemsPerMem);

%detect how many particpants there are as indicated by the number of cols
data = experiment_session{1, 1};
[~, D_Cols] = size(data);
data_storage = cell(1, LenItems);

for i = 1:D_Cols    
    for j = 1:LenItems
        scope1 = data{1, i}{1, 1}{1, j};
        if i == 1
        data_storage{1, j} = scope1;
        else
        data_storage{1, j} = [data_storage{1, j}; scope1];
        end       
    end     
end 
end 
