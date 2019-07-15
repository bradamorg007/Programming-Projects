
function session = MEG_ICARejectComponents(session)
   
cfg = [];
cfg.channel = [1:10]; 
cfg.continuous='no';
cfg.viewmode = 'component'; 
cfg.layout = 'neuromag306planar.lay';
ft_databrowser(cfg, session.data.ICACalculated.comp);

inputTrig = true;
stop = false;
while stop == false
    
    try
        if inputTrig == true
            disp(' ')
            reject_ICA_comp = input('ICA components to reject:: ');
            disp(' ')
        end
        q1 = input('Do you wish to exit ICA component rejection? Enter reject_ICA_comp to see rejection list::  ', 's');
        
        if strcmpi(q1, 'y') || strcmpi(q1, 'yes') || strcmpi(q1, 'e') || strcmpi(q1, 'exit')
            non_int_match = false;
            for i = 1:length(reject_ICA_comp)
                if ~isa(reject_ICA_comp(i), 'double')
                    disp('')
                    disp('The ICA componenet rejection list cannot be accepted as some of the values are not integers');
                    non_int_match = true;
                    break;
                end
            end
            
            if non_int_match == false
                stop = true;
            end
            inputTrig = true;
            
        elseif strcmpi(q1, 'reject_ICA_comp')
            disp('========================================================')
            disp('Current Rejection List: ');
            disp(reject_ICA_comp);
            disp('');
            inputTrig = false;
        end
        
    catch
        disp(' ')
        disp('Invalid Expression to input vectors use [] notation')
    end
    
end

cfg = [];  
cfg.component = reject_ICA_comp;  
data_clean = ft_rejectcomponent(cfg, session.data.ICACalculated.comp, session.data.ICACalculated.data_all_resamp); 

session.data.ICADataClean = data_clean;

end 