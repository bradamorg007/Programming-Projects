% Participants details 

function [ParticpantInfo] = Demographics()
try
fig1 = figure('units','normalized','outerposition',[0 0 1 1]);
%[0.2 0.35 0.2 0.6]

    set(fig1, 'MenuBar', 'none');
    set(fig1, 'ToolBar', 'none');
    set(fig1,  'Resize',  'off');
    
  pnl1 = uipanel(fig1,'Title', 'Particpant Details', 'FontSize',12, 'FontWeight','Bold',...
                'BackgroundColor','white',...
                'Position',[0.02 0.02 0.965 0.95]);
    
  subpnl = uipanel(pnl1,'Position',[0.05, 0.03, 0.9, 0.95]);
            
  name1_txt = uicontrol(subpnl,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',[0.25 0.45 0.5 0.5] ,...
                        'FontSize', 16, ...
                        'FontWeight', 'Bold',...
                        'String', 'First Name',...
                        'Visible', 'on');
                    
  FirstName = uicontrol(subpnl,'style','edit',...
                    'units', 'normalized',...
                    'position', [0.1 0.8 0.8 0.1],...
                    'FontSize', 14);
                
  name2_txt = uicontrol(subpnl,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',[0.25 0.25 0.5 0.5] ,...
                        'FontSize', 16, ...
                        'FontWeight', 'Bold',...
                        'String', 'Last Name',...
                        'Visible', 'on');
                    
   LastName = uicontrol(subpnl,'style','edit',...
                    'units', 'normalized',...
                    'position', [0.1 0.6 0.8 0.1],...
                    'FontSize', 14);  
                
  Age_txt = uicontrol(subpnl,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',[0.25 0.05 0.5 0.5] ,...
                        'FontSize', 16, ...
                        'FontWeight', 'Bold',...
                        'String', 'Age',...
                        'Visible', 'on');
                    
   Age = uicontrol(subpnl,'style','edit',...
                    'units', 'normalized',...
                    'position', [0.1 0.4 0.8 0.1],...
                    'FontSize', 14);
  
   gender_txt = uicontrol(subpnl,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',[0.25 0.3 0.5 0.05] ,...
                        'FontSize', 16, ...
                        'FontWeight', 'Bold',...
                        'String', 'Gender',...
                        'Visible', 'on');
                             
  Gender = uicontrol(subpnl,'Style', 'popup',...
           'String', {'Male', 'Female'},...
           'units', 'normalized',...
           'Position', [0.1 0.18 0.8 0.1],...
           'FontSize', 14); 
     
  ethnicity_txt = uicontrol(subpnl,'Style', 'Text',... 
                        'Units', 'Normalized',... 
                        'Position',[0.25 0.1 0.5 0.1] ,...
                        'FontSize', 16, ...
                        'FontWeight', 'Bold',...
                        'String', 'Ethnicity',...
                        'Visible', 'on');
                             
  Ethnicity = uicontrol(subpnl,'Style', 'popup',...
           'String', {'White Scottish', 'White Welsh', 'White British', 'White Irish',...
                      'White and Black Caribbean', 'White and Black African',...
                      'Asian / Asian British', 'Indian', 'Pakistani',...
                      'African',  'Caribbean', 'Arab', 'Other'},...
           'units', 'normalized',...
           'Position', [0.1 0.04 0.8 0.1]);
       
  submit_button = uicontrol(subpnl,'style', 'pushbutton',... 
                    'string', 'Submit', ...
                    'units', 'normalized',... 
                    'position', [0.4 0.01 0.2 0.06],... 
                    'callback', {@submit_func, FirstName,LastName,...
                                               Age,Gender,Ethnicity, fig1});
    
uiwait(fig1)

disp('I MADE IT TO THE OTHER SIDE')
ParticpantInfo = getappdata(fig1, 'ParticpantData');
close(fig1)
catch Exception
    
        if  strcmp(Exception.identifier,'MATLAB:hg:dt_conv:Matrix_to_HObject:BadHandle') == 1 ||  strcmp(Exception.identifier, 'MATLAB:class:InvalidHandle') == 1
        msg = questdlg('The Experimental Session has been terminated. Please note that current session data has not been saved', 'Session Termination', 'OK', 'OK');
            switch msg
                case 'OK'
                    %LINK BACK TO EXPERIMENTAL START PAGE
                    Main_Menu()
            end 
         end 
end
    function submit_func (source, event, FirstName,LastName,Age,Gender,Ethnicity, fig1)

        % The Black Hole 
        disp(Gender.Value)
        FirstName = convertCharsToStrings(oricle_input_check5_RemoveWhiteSpace(FirstName.String));
        LastName = convertCharsToStrings(oricle_input_check5_RemoveWhiteSpace(LastName.String));
        Age =  oricle_input_check1(oricle_input_check5_RemoveWhiteSpace(Age.String), 'y', 'mixed=n');
        Gender = convertCharsToStrings(Gender.String(Gender.Value));
        Ethnicity = convertCharsToStrings(Ethnicity.String(Ethnicity.Value));


        ParticpantData = table(FirstName, LastName, Age, Gender, Ethnicity);
        %use appdata function to pass data out of a callback function
        setappdata(fig1, 'ParticpantData', ParticpantData);
        uiresume(fig1)
      end 
end          