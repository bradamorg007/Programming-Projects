
function session = MEG_sessionInit(session)

    if isfield(session.paths, 'loadSession')
        disp('Loading Session - please wait...')
        load(session.paths.loadSession);
        disp('');
        disp('*** Load Completed! ***')
    
    elseif isfield(session.paths, 'sessionFolderPath') && isfield(session.paths, 'sessionName') && isfield(session.paths, 'rawData')
        session.paths.sessionFolderPath = [session.paths.sessionFolderPath, session.paths.sessionName, '/'];
    else
        error('The Session can not be initilised. Missing session Folder or session Name or session RawData or loadFileName paths');
    end 

    if ~exist(session.paths.sessionFolderPath, 'dir')
        mkdir(session.paths.sessionFolderPath);
    end 

end