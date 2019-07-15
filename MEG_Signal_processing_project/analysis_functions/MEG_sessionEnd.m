
function [] = MEG_sessionEnd(session)

    if isfield(session.paths, 'sessionFileName')
        save([session.paths.sessionFolderPath,session.paths.sessionFileName], 'session')
    end 

end 