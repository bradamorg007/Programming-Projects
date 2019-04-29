function exit = User_input_checks(absolute_path,tolerance, grid_size, multiplots)

if ~exist(absolute_path,'dir'), mkdir(absolute_path); 
elseif exist(absolute_path,'dir')
    Q = questdlg('The folder name already exists, The data will be overwritten', 'Save Option', 'Ok', 'Cancel', 'Cancel');
    if strcmpi(Q, 'Ok')
        rmdir(absolute_path, 's')
        mkdir(absolute_path)
        exit = 0;
    elseif strcmpi(Q, 'Cancel') || strcmpi(Q, '')
        exit = 1;
    end 
end
exit =0;
len_tolerance = length(tolerance);
len_gridsize = length(grid_size);

if ~isequal(len_tolerance, len_gridsize)
    error('Grid size and Tolerance vector lengths must be equal');
end 
if ~strcmpi(multiplots, 'yes') && len_tolerance > 1 
    error('If you wish to view data per timestep, run each trial with varying tolerance seperatley in different folders')
end 