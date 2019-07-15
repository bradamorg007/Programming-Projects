
function [session] = MEG_filterDataByEvents(session, searchParams)
  
maxLenOfEvents = length(session.data.events{2, 1});
disp(" ")
disp("====================================================================")
disp("                    MEG_FILTER DATA BY EVENTS : ACTIVE")
disp(" ")
    searchFieldStore = cell(1, length(searchParams));
    filterdDataIndexStore = cell(1, length(searchParams));
    
    % pre process search paramters strings. remove whitespace and reshape
    % so that the whole vector can be compared at a time.
    
    for i = 1:length(searchParams)
        [searchParmsSplit] = split(searchParams{i}, ',');
        searchParmsSplit = searchParmsSplit';
        
        if isvector(searchParams)
            
        else
            disp(' ');
            disp('*** ERROR ***');
            disp('BRAD ISSUED ERROR: search paramteres must be inputted as a cell vector, with each cell representing one search instance');
            
        end
        
        searchParmsSplit = string(searchParmsSplit);
        searchParmsSplit = strtrim(searchParmsSplit);
        searchParmsSplit(find(strcmp(searchParmsSplit, ""))) = [];
        
        if (length(searchParmsSplit) > 1 && length(searchParmsSplit) < maxLenOfEvents)
                disp(" ");
                disp("*** WARNING ***");
                error(strcat(" The system has not yet been updated to handle irregular shaped search vectors. The search field: ", searchParams{i}, " size is greater than 1 and smaller than the maximum event conditions = ", string(maxLenOfEvents)));
                disp("*** ******* ***");
                disp(" ");
            
        end 
        
        searchFieldStore{i} = searchParmsSplit;
       
        
    end
    
        
        % loop through the search params and the events for each trial
        % if the event and current ith search param match then record its
        % index as this will tell use which trials to keep.
        for i = 1:length(searchFieldStore)
            
            for j = 1:length(session.data.events(2, :))
                
                event = session.data.events{2, j};
                logicalMatch = strcmp(event, searchFieldStore{i});
                
                if sum(logicalMatch) == length(searchFieldStore{i})
                    
                    filterdDataIndexStore{i}(end + 1) = j;
                    
                end
                
            end
            
            if (isempty(filterdDataIndexStore{i}))
                disp(" ");
                disp("*** WARNING ***");
                disp(strcat("No Events were found matching the search field: ", searchParams{i}));
                disp("*** ******* ***");
                disp(" ");
                
            else
                disp(" ");
                disp("*** :) :D SUCCESS :) :D ***");
                disp(strcat("An Event was found matching the search field: ", searchParams{i}));
                disp("*** ******* ***");
                disp(" ");
            end 
            
        end
        
       
        % uses the filter indexes to extract the relevent trials for each
        % search paramter
        
        % first check that session.data.ICADataClean exists - this data will be
        % filtered
        
        if (isfield(session.data, 'ICADataClean'))
            
            if (length(searchParams) == length(filterdDataIndexStore))
                
                session.data.filteredData = cell(1, length(searchParams));
                
                for j = 1:length(filterdDataIndexStore)
                    
                    session.data.filteredData{j}.label = searchParams{j};
                    session.data.filteredData{j}.filterIndexes = filterdDataIndexStore{j};
                    
                    newTime = session.data.ICADataClean.time(filterdDataIndexStore{j});
                    newTrial = session.data.ICADataClean.trial(filterdDataIndexStore{j});
                    newTrialinfo = session.data.ICADataClean.trialinfo(filterdDataIndexStore{j});
                    
                    copyICAData = session.data.ICADataClean;
                    copyICAData.time = newTime;
                    copyICAData.trial = newTrial;
                    copyICAData.trialinfo = newTrialinfo;
                    
                    session.data.filteredData{j}.data = copyICAData;
                    
                end
                
            else
                disp(' ');
                disp('*** ERROR *** ');
                error('BRAD ISSUED ERROR: The length of the search parameters and filteredIndexStore are not equal');
                
            end
            
        else
            disp(' ');
            disp('*** ERROR *** ');
            error('BRAD ISSUED ERROR: filtering data non-Existant : session.data does not contain a ICADataClean field: please run MEG_ICARejectComponents(session).m;');
        end
  disp("====================================================================")
  disp(" ")
     
end