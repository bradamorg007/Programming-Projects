% DATA ANLAYSIS 

function Data_Analysis()
fig = figure('units','normalized','outerposition',[0.25 0.3 0.3 0.4]);
    set(fig, 'MenuBar', 'none');
    set(fig, 'ToolBar', 'none');
    set(fig,  'Resize',  'off');

%==========================================================================
%                           Panels & Sub-Panels
%==========================================================================
    
pnl1 = uipanel(fig,'Title','Descriptives & Visualisations','FontSize',12, 'FontWeight','Bold',...
                'BackgroundColor','white',...
                'Position',[0.05 0.05 0.9 0.75],...
                'Fontsize', 8);
  
pnl2 = uipanel(fig,'FontSize',12, 'FontWeight','Bold',...
                'BackgroundColor','white',...
                'Position',[0.05 0.81 0.91 0.17],...
                'Fontsize', 8);
subpn0 = uipanel(pnl2,'Position',[0.01, 0.1, 0.98, 0.8]);
subpnl = uipanel(pnl1,'Position',[0.05, 0.6, 0.9, 0.4]);
subpn2 = uipanel(pnl1,'Position',[0.05, 0.32, 0.9, 0.25]);
subpn3 = uipanel(pnl1,'Position',[0.05, 0.02, 0.9, 0.3]);

%==========================================================================
%                           Title & Back-Button
%==========================================================================
Title = uicontrol(subpn0,'Style', 'text',... 
                    'Units', 'Normalized',... 
                    'Position', [0.05, 0.2, 0.9, 0.8],...
                    'FontSize', 14, ...
                    'FontWeight', 'Bold',...
                    'String', 'Data Analysis');
               
back = uicontrol(subpn0,'Style', 'Pushbutton',... 
                    'Units', 'Normalized',... 
                    'Position', [0.01, 0.15, 0.13, 0.75],...
                    'FontSize', 8, ...
                    'FontWeight', 'normal',...
                    'String', 'Back',...
                    'Callback', {@Backfunc, fig});
%==========================================================================
%                           Decriptives & Visualisation
%==========================================================================
Show_Data = uicontrol(subpnl,'Style', 'popup',... 
                    'Units', 'Normalized',... 
                    'Position', [0.05, 0.1, 0.9, 0.4],...
                    'FontSize', 8, ...
                    'FontWeight', 'normal',...
                    'String', 'Currently Uploaded Files');
                
Data = uicontrol(subpnl,'Style', 'Pushbutton',... 
                    'Units', 'Normalized',... 
                    'Position', [0.05, 0.6, 0.9, 0.35],...
                    'FontSize', 8, ...
                    'FontWeight', 'normal',...
                    'String', 'Insert Session Data',...
                    'Callback', {@Insert_Data, fig Show_Data});

                
visual_static_txt = uicontrol(subpn2,'Style', 'text',... 
                    'Units', 'Normalized',... 
                    'Position', [0.05, 0.6, 0.9, 0.35],...
                    'FontSize', 8, ...
                    'FontWeight', 'Bold',...
                    'String', 'Visualisation');
                
visual_graphs = uicontrol(subpn2,'Style', 'popup',...
           'String', {'Histogram', 'Bar Graph'},...
           'units', 'normalized',...
           'Position', [0.1 0.6 0.8 0.02],...
           'FontSize',8); 
       
visual_generate = uicontrol(subpn3,'Style', 'pushbutton',... 
                    'Units', 'Normalized',... 
                    'Position', [0.05, 0.55, 0.9, 0.4],...
                    'FontSize', 8, ...
                    'FontWeight', 'normal',...
                    'String', 'Generate Plot',...
                    'Callback', {@visualisation, fig,Show_Data,visual_graphs});
                
descriptives = uicontrol(subpn3,'Style', 'pushbutton',... 
                    'Units', 'Normalized',... 
                    'Position', [0.05, 0.1, 0.9, 0.4],...
                    'FontSize', 8, ...
                    'FontWeight', 'normal',...
                    'String', 'Descriptive Statistics',...
                    'Callback', {@Descriptives, fig,Show_Data});
    

                
%==========================================================================
%                             Functions
%==========================================================================

%INSERT SESSION DATA

    function demographs(source, event)
    end 
    function Backfunc(source, event, fig)
        Main_Menu();
        close(fig);
    end 
function Insert_Data(source, event, fig, Show_Data)
try
[filename, ~, ~, experiment_session] = OpenFileFunc('Open TEST', {'experiment_session'});
for i = 1:4
filename(length(filename)) = [];
end
current_entries = cellstr(get(Show_Data, 'String'));
current_entries{end + 1} = filename;
demographics_displayer(experiment_session);
Sorted_Data = DataSorter1_1(experiment_session);
Sorted_Data{2, 1} = experiment_session{1, 2};
setappdata(fig, filename, Sorted_Data);
setappdata(fig,'current_entries', current_entries);
set(Show_Data, 'String', current_entries);

catch Exception
    disp(Exception)
end
end 

    function Descriptives(source, event,fig,Show_Data)
       try  
        data_list = Show_Data.String;
        data_selection = data_list(Show_Data.Value);
        
        if strcmp(data_selection, 'Currently Uploaded Files')
            questdlg('To generate decriptive statistics. Please select an experiment session','OK', 'OK', 'OK');
        else 
           experiment_session = getappdata(fig,data_selection{1, 1});
           Descriptives_HighLevel(experiment_session);
           %close(fig);
        end
        
       catch Exception
           if strcmp(Exception.identifier,'MATLAB:cellRefFromNonCell')
             questdlg('To generate decriptive statistics. Please select an experiment session','OK', 'OK', 'OK');
           end 
       end 
    end 

    function visualisation(source, event,fig,Show_Data,visual_graphs)
       try  
        data_list = Show_Data.String;
        data_selection = data_list(Show_Data.Value);
        
        graph_list = visual_graphs.String;
        graph_selection = graph_list(visual_graphs.Value);
        
        if strcmp(data_selection, 'Currently Uploaded Files')
            questdlg('To generate Data Graphs Please select an experiment session','OK', 'OK', 'OK');
        else 
           experiment_session = getappdata(fig,data_selection{1, 1});
           GraphBuilder(experiment_session, graph_selection{1, 1});
           %close(fig);
        end
        
       catch Exception
           if strcmp(Exception.identifier,'MATLAB:cellRefFromNonCell')
             questdlg('To generate Data Graphs Please select an experiment session','OK', 'OK', 'OK');
           end 
       end 
    end 
end 

