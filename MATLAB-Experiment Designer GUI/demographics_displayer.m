
function demographics_displayer(experiment_session)

[~, N] = size(experiment_session{1,1});

Age_store = zeros(1, N);
for i = 1:N
    scope = experiment_session{1, 1}{2, i}{1, 3};
    Age_store(i) = scope;
end 

mean_age = mean(Age_store);
min_age = min(Age_store);
max_age = max(Age_store);

male = 0;
female = 0;
for j = 1:N
   scope1 = experiment_session{1, 1}{2, j}{1, 4}; 
   if strcmp(scope1, "Female")
       female = female + 1;
   elseif strcmp(scope1, "Male")
       male = male + 1;
   end 
end 

vector = [N, mean_age, min_age, max_age, male, female];

 figP = figure('units','normalized','outerposition',[0.1 0.4 0.54 0.233]);
            set(figP, 'MenuBar', 'none');
            set(figP, 'ToolBar', 'none');
            %set(fig1,  'Resize',  'off');
            Title = strcat('Demographics');
            set(figP, 'Name',Title);
            
           T = uitable(figP, 'Data', vector);

           set(T, 'Units', 'normalized');
           set(T, 'Position', [0.01, 0.8, 0.5 0.5],... 
                  'outerposition',[0 0 1 1],...
                  'innerposition', [0 0 1 1],...
                  'FontSize', 11, 'ColumnName',{'Sample Size', 'Mean_age',...
                  'Min_Age', 'Max_Age', 'Male', 'Female'},'RowName','Details');



end 