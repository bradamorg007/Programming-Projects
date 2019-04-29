
%Main Experiment loop handler, High level that deals with user input
%seperated into sub_functions so that Exceptions or end processing requests
%can occur without statements below such points being run.

function Run_Setup()
try

msg1 = questdlg('Do you want to save particpant data to a previous experiment session?',...
               'Particpant Data','Yes');
previous_file_selected = 0; 
%Switch statement that determines whether the user wants to add further particpants to
%a previously run experiment session.
switch msg1
    case 'Yes'
        %Open file func is helper function for opening and verifying the
        %corrrect files, copatible with the program are being opened.
        [~, ~, expfull_path, experiment_session] = OpenFileFunc('Open TEST', {'experiment_session'});  
        previous_file_selected = 1;
        part2(experiment_session,previous_file_selected, expfull_path);

    case 'No'
        expfull_path = [];
        experiment_session = cell(2, 1);  
        part2(experiment_session, previous_file_selected, expfull_path);
    case 'Cancel'
        Main_Menu()
    case ''
        Main_Menu()
end
catch Exception
    if strcmp(Exception.identifier,'MATLAB:unassignedOutputs')
        Main_Menu()
    end 
end 
end 

function part2(experiment_session, previous_file_selected, expfull_path)
try
%Open Experimental Data 
%ADD ALL BELOW TO NESTED FUNCTION SO CANCEL DOES NOT EXECUTE THIS
msg2 = questdlg('Please select a Dataset to use for the session', 'Ok', 'Ok', 'Ok');
if isempty(msg2) == 1
    Main_Menu()
else
    %Open and Check File Function
[~, ~, stimfullpath, ProbeData,ProbeSequence,StimulusData, ExperimentSettingsTable] = OpenFileFunc('Open TEST', {'ProbeData','ProbeSequence',...
                                                                                        'StimulusData', 'ExperimentSettingsTable'}); 
    %Reads the ID codes of Data and experiment session. It does not allow
    %for multiple datasets to be used on a single experiment_session as
    %this would confound resulst by distorting clear unbiased, unaltered isolation between
    %conditions
    [experiment_session, ExperimentSettingsTable] = ReadTag(experiment_session, ExperimentSettingsTable);
part3(experiment_session, ProbeData,ProbeSequence,StimulusData,...
      ExperimentSettingsTable, previous_file_selected, expfull_path, stimfullpath);
end  
catch Exception
    if strcmp(Exception.identifier,'MATLAB:unassignedOutputs')
        Main_Menu()
    end 
end 
end

function part3(experiment_session, ProbeData,ProbeSequence,StimulusData,...
               ExperimentSettingsTable,previous_file_selected, expfull_path, stimfullpath)
try
%check variables exist
switch previous_file_selected
    case 0
        questdlg('Please select a savepath for the current experiment session','Save Session','Ok', 'Ok');
        response = 0;
        while response == 0
            [filename,path] = uiputfile('Experiment_Session1.mat','Save Workspace As');
            SavePath = convertCharsToStrings(strcat(path, filename));
            if isequal(filename, 0) == 1 || isequal(path, 0) == 1
                questdlg('Error: Please Save the Current Experiment Session','Error','Ok', 'Ok');
                response = 0;
            else
                response = 1;
            end    
        end 
end 
%==========================================================================
%                     Run Demographics and Exp loop
%==========================================================================
%Calls Demographics func to get user info
ParticpantInfo = Demographics();
[participant, terminated] = Experimental_Loop(StimulusData,ProbeData,ProbeSequence,ExperimentSettingsTable);

%==========================================================================
%                           Store Experimental Data
%==========================================================================
%Calculate current length of experiment_session add new particpant to the
%end of it.
[~,experiment_sessionCols]  = size(experiment_session{1,1});

%Look at last added cell to see if its populated by previous partcipant
%entry
lookBack = experiment_session{1, 1}(:, experiment_sessionCols);

if isempty(lookBack{1,1}) && isempty(lookBack{2, 1})
    experiment_session{1, 1}{1, experiment_sessionCols} = participant;
    experiment_session{1, 1}{2, experiment_sessionCols} = ParticpantInfo;
else 
    experiment_session{1, 1}{1, experiment_sessionCols + 1} = participant;
    experiment_session{1, 1}{2, experiment_sessionCols + 1} = ParticpantInfo;
end 

%==========================================================================
%                           Save experimental Data
%==========================================================================
if ~isequal(terminated, 3)
    switch previous_file_selected
        case 0
            save(SavePath, 'experiment_session');
            save(stimfullpath, 'StimulusData', 'ProbeData', 'ProbeSequence', 'ExperimentSettingsTable');
            questdlg('Session Data has been successfully','Save Session','Ok', 'Ok');
            disp('ITS ALL GOOD')
            Main_Menu();
        case 1
            save(expfull_path, 'experiment_session');
            save(stimfullpath, 'StimulusData', 'ProbeData', 'ProbeSequence', 'ExperimentSettingsTable')
            questdlg('Session Data has been successfully','Save Session','Ok', 'Ok');  
            Main_Menu();
    end 
else
end
catch Exception

end 
end 





