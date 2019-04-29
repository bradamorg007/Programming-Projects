function [Output1, Output2] = ReadTag(x, y)
%Make sure experimental sessions cannot be contaminated by mismatching
%multiple experimental condition settings into one session. Every dataset
%and experiment_session(particpants data) gets an ID_Code that can be
%tracked.
 try  
  [xrows, xcols] = size(x);
  [~, ycols] = size(y);
    
  if xrows == 1 && xcols == 2 && ycols == 13 %BOTH TAGS
      xID = x{2}{1, 13};
      yID = y{1, 13};
      
      if strcmp(xID, yID) == 1
          %They Match
          questdlg('Inserted Stimulus Dataset File and Experiment Session File has been Accepted','Attention','OK', 'OK');
      else
          %They Dont match
          error('ERROR: The selected experimental session was run using a different stimulus dataset. Adding a new dataset will contaminate the results. Please create a new experimental Session');
      end
        
      Output1 = x;
      Output2 = y;
  elseif xrows == 2 && xcols == 1 && ycols == 12 %NO TAGS
      [x, y, ID_CODE] = Tag(x, y, 'create');
      msg = strcat('Newly Generated Experiment Session Detected \\ Newly Generated Dataset Detected: Both Files have been Linked ID_CODE = ', ID_CODE);
      questdlg(char(msg), 'Warning', 'OK', 'OK');
      Output1 = x;
      Output2 = y;
  elseif xrows == 2 && xcols == 1 && ycols == 13 %exp_session No TAG, STimData = TAG 
      [x, y, ID_CODE] = Tag(x, y, 'copy');
      msg = strcat('Newly Generated Experiment Session Detected \\ Previously Used Dataset Detected: The experiment session has been assigned the ID_code of the previous Dataset: ID_CODE = ', ID_CODE);
      questdlg(char(msg), 'Warning', 'OK', 'OK');
      Output1 = x;
      Output2 = y;
  elseif xrows == 1 && xcols == 2 && ycols == 12 %exp_session TAG, StimDATA NO TA
      error('ERROR: The selected experimental session was run using a different stimulus dataset. Adding a new dataset will contaminate the results. Please create a new experimental Session');
  else 
      error('SYSTEM FAILURE: FUNCTION - TAG_READER line 57');
  end 
catch Exception
      disp(Exception)
      if strcmp(Exception.message,'ERROR: The selected experimental session was run using a different stimulus dataset. Adding a new dataset will contaminate the results. Please create a new experimental Session' )
       msg = questdlg('ERROR: The selected experimental session was run using a different stimulus dataset. Adding a new dataset will contaminate the results. Please create a new experimental Session', 'DATA ERROR','OK', 'OK');
        if strcmp(msg, 'OK') || strcmp(msg, '')
            Main_Menu();
        end 
      end 
end 
  end 