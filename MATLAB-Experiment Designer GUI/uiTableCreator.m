%Uitable creator 

function uiTableCreator(varargin)

defaultData = [];
defaultTableType = [];
defaultConditions = [];
defaultMeasure = [];
defaultPercentile = [];


p = inputParser;

addParameter(p,'Data',defaultData);
addParameter(p,'TableType',defaultTableType);
addParameter(p,'Conditions',defaultConditions);
addParameter(p,'Measure',defaultMeasure);
addParameter(p,'Percentile', defaultPercentile);
parse(p,varargin{:});

Data = p.Results.Data;
TableType = p.Results.TableType;
Conditions = p.Results.Conditions;
Measure = p.Results.Measure;
Percentile = p.Results.Percentile;
ColNames = {'Mean','Median','Mode(x)',...
                               'Sum','Skewness',...
                               'Kurtosis','Std',...
                               'Variance','Min',...
                               'Max'};

switch TableType
    case 'Descriptives'
         [DataRows, ~] = size(Data);
         name = 'Condition: Items = ';
         for i = 1:DataRows
             num = string(Conditions(i));
             Name{i} = strcat(name,num);
         end 

         
        figT = figure('units','normalized','outerposition',[0.1 0.4 0.715 0.233]);
            set(figT, 'MenuBar', 'none');
            set(figT, 'ToolBar', 'none');
            %set(fig1,  'Resize',  'off');
            Title = strcat('Descriptive Statistics:',Measure);
            set(figT, 'Name',Title);
            
           T = uitable(figT, 'Data', Data);
             get(T)
           set(T, 'Units', 'normalized');
           set(T, 'Position', [0.01, 0.8, 0.5 0.9],... 
                  'outerposition',[0 0 1 1],...
                  'innerposition', [0 0 1 1],...
                  'FontSize', 10, 'ColumnName', ColNames,'RowName', Name);
              
    case 'Percentiles'
        
         name = 'Condition: Items = ';
         for i = 1:length(Conditions)
             num = string(Conditions(i));
             Name{i} = strcat(name,num);
         end 
        for j = 1:length(Percentile)
        word1 = 'Percentile =';
        word2 = 'th';
        word1 = strcat(word1, Percentile(j));
        word1 = strcat(word1, word2);
        words{j} = word1;
        end
             
        figP = figure('units','normalized','outerposition',[0.1 0.4 0.54 0.233]);
            set(figP, 'MenuBar', 'none');
            set(figP, 'ToolBar', 'none');
            %set(fig1,  'Resize',  'off');
            Title = strcat('Descriptive Statistics:',Measure);
            set(figP, 'Name',Title);
            
           T = uitable(figP, 'Data', Data);
             get(T)
           set(T, 'Units', 'normalized');
           set(T, 'Position', [0.01, 0.8, 0.5 0.9],... 
                  'outerposition',[0 0 1 1],...
                  'innerposition', [0 0 1 1],...
                  'FontSize', 11, 'ColumnName', Name,'RowName',words );
              
     
        
end 




 
end 



