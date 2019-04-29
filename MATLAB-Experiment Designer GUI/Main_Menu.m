%Welcome !!! Thanks for checking out my Sternberg program. There are many
%files to look through, I really do apologies for that, I always seem to get carried away :). 

%Please Note that following program was created in the latest MATLAB
%version R2017b. Some functions such as convertCharsToStrings may not work
%on later version such as R2017b. 

%If all fails please contact me at bxm604@student.bham.ac.uk. Id be more
%than happy to demo the program on my laptop. Thanks :)

function Main_Menu()

%Define the Main Menu page Using uicontrol objects

%define main figure parameters
fig1 = figure('units','normalized','outerposition',[0.2 0.35 0.5 0.3]);

    set(fig1, 'MenuBar', 'none');
    set(fig1, 'ToolBar', 'none');
    set(fig1,  'Resize',  'off');
    
    pnl1 = uipanel(fig1,'FontSize',12, 'FontWeight','Bold',...
                'BackgroundColor','white',...
                'Position',[0.02 0.05 0.965 0.9]);
            
  %define Menu_Menu Page Objects 
    title = uicontrol(pnl1,'Style', 'Text',... 
                    'Units', 'Normalized',... 
                    'Position', [0.05, 0.8, 0.9, 0.15],...
                    'FontSize', 14, ...
                    'FontWeight', 'Bold',...
                    'String', 'Sternberg Experiment: Main Menu');
                
   generate_data_button = uicontrol(pnl1, 'style', 'pushbutton', ...
                              'string', 'Generate DataSet',...
                              'units', 'normalized',...
                              'position', [0.3 0.3 0.4 0.2],...
                              'callback', {@generate_data, fig1});

runExperiment_button = uicontrol(pnl1,'style', 'pushbutton', ...
                              'string', 'Run Experiment: Sternberg Task',...
                              'units', 'normalized',...
                              'position', [0.3 0.55 0.4 0.2],...
                              'callback', {@Run_Experiment, fig1});
            
data_button = uicontrol( 'style', 'pushbutton',... 
                    'string', 'Data Analysis', ...
                    'units', 'normalized',... 
                    'position', [0.31 0.1 0.386 0.19],...
                    'callback', {@Data_Analysisfunc, fig1});
                
 

                         
%==========================================================================
%                         CALLBACK FUNCTIONS
%==========================================================================
%Functions link 3 other GUI pages that run High level processing
function Run_Experiment(source, event, fig1)
    close(fig1)
    Run_Setup();
end 
function generate_data(source, event, fig1)
    close(fig1)
experimental_settings();
end 
function Data_Analysisfunc(source, event, fig1)
    close(fig1)
    Data_Analysis();
end 

end 