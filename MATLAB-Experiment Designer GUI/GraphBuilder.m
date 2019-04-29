
function GraphBuilder(Data, GraphType)

switch GraphType
    case 'Histogram'
        
        fig1 = figure('units','normalized','outerposition',[0 0 1 1]);

        %set(fig1, 'MenuBar', 'none');
        %set(fig1, 'ToolBar', 'none');
        %set(fig1,  'Resize',  'off');
       
        ItemsPerMem = Data{2, 1}{1, 2};
        %Detect How Many Conditions There Are 
        Len = length(ItemsPerMem);
        conditions = cell(1, Len);
        for i = 1:length(ItemsPerMem)
            conditions{i} = strcat("Items Per Memory Load = ",string(ItemsPerMem(i)));
        end 
        data_scope = Data{1, 1}(:, 2);
       % K = round(1 + 3.322 * log10(rows));
        h_graph = histogram(data_scope);
        xlabel('Reaction Times');
        ylabel( 'Frequency', 'FontWeight', 'bold');
        title('Histogram of Reaction Times Per Memory Load');
        grid on
        axis_h = gca;
        set(axis_h, 'position', [0.2 0.1100 0.7750 0.8150]);
        
        Show_Data = uicontrol(fig1,'Style', 'popup',... 
                    'Units', 'Normalized',... 
                    'Position', [0.005, 0.1, 0.16, 0.25],...
                    'FontSize', 10, ...
                    'FontWeight', 'normal',...
                    'String',conditions );
                        
       Overlay_data = uicontrol(fig1,'Style', 'togglebutton',... 
                    'Units', 'Normalized',... 
                    'Position', [0.005, 0.2, 0.1, 0.05],...
                    'FontSize', 10, ...
                    'FontWeight', 'normal',...
                    'String','Overaly Data');
                    %'Callback', {@Refreshfunc, fig1,Data, Show_Data, h_graph});
                    
       clear_data = uicontrol(fig1,'Style', 'pushbutton',... 
                    'Units', 'Normalized',... 
                    'Position', [0.005, 0.25, 0.1, 0.05],...
                    'FontSize', 10, ...
                    'FontWeight', 'normal',...
                    'String','Back',...
                    'Callback', {@Gfunc, fig1});
                                   
                
        refresh_data = uicontrol(fig1,'Style', 'pushbutton',... 
                    'Units', 'Normalized',... 
                    'Position', [0.005, 0.1, 0.1, 0.1],...
                    'FontSize', 10, ...
                    'FontWeight', 'normal',...
                    'String','Update',...
                    'Callback', {@Refreshfunc, fig1,Data, Show_Data, h_graph,...
                                Overlay_data});
                                
      case 'Bar Graph'
          vector = [1 0 0 0 0 0 0 0 0 0];
            LenData = length(Data);
            for j = 1:LenData
                   Descriptive_Table = Descriptives_LowLevel(Data{1,j}(:, 2), vector);
                   Descriptive_store(j, :) = Descriptive_Table;
            end 
        ItemsPerMem = Data{2, 1}{1, 2};
        %Detect How Many Conditions There Are 
        Len = length(ItemsPerMem);
        conditions = cell(1, Len);
        for i = 1:length(ItemsPerMem)
            conditions{i} = strcat("Memory Load = ",string(ItemsPerMem(i)));
        end 
    fig2 = figure('units','normalized','outerposition',[0 0 1 1]);
    set(fig2,  'Resize',  'off');
    bar(Descriptive_store(:, 1))
    grid on
    xlabel('Experimental Conditions', 'FontWeight', 'bold')
    ylabel('Mean Reaction Times', 'FontWeight', 'bold')
    title('Bar Graph: Mean Reaction Times For Experimental Session')
    set(gca,'xticklabel',conditions);
            
end 

    function Gfunc(source, event, fig1)
       close(fig1)
    end 

    function Refreshfunc(source, event,fig1,Data, Show_Data, h_graph,Overlay_data)
        
        data_selection = Show_Data.Value;
        data_selection = double(data_selection);
        current_data = h_graph.Data;
        selected_data = Data{1, data_selection}(:, 2);
        
        switch Overlay_data.Value
            case 0
                set(h_graph, 'Data', selected_data);
                refresh(fig1); 
            case 1
                hold on
               histogram(current_data);
               histogram(selected_data);
                hold off
                refresh(fig1)
        end 
                
    end 


end 