% Agent model Social Segregation 

%PARAMETERS DESCRIPTIONS
% Gridsize: Size of simulation space 
% group 1 = +1 group 2 = -1
% tolerance is just a percentage threshold of required simularity with agent and neighbours
% percentage(1): percentage of total number of agents in world space vs empty space
% percentage(2): percentage of group1 agents with respect to the total population of agents in the world space
% timesteps: Maximum number of iterations per simulation run

function Social_Seg_Model()
%MODEL PARAMTERS
grid_size = [70];
groups = [1, -1];
tolerance = [0.8];
percentage = [50,50];
timesteps =200;

%SAVE & PLOTTING OPTIONS
multiplots = 'no';
saveplots = 'yes';
foldername = 'Trial1';
path = 'C:\Users\Novus\Desktop\Mind_Brain_Model\Workshop 8\MyVersion\';
absolute_path = strcat(path, foldername);

% BASIC INPUT CHECKS - Avoid model from crashing
exit = User_input_checks(absolute_path,tolerance, grid_size, multiplots);
if isequal(exit, 1), return, end

%MODEL LOOP
    for j = 1:length(tolerance)
        
        stopdrawing = 0;
        
        %define empty grid world 
        gridworld = zeros(grid_size(j), grid_size(j));
        num_range = grid_size(j)^2;
        
        %calculate population percentages relative to empty space and per
        %group.
        percentage1 = percentage/100;
        population = round(num_range*percentage1(1));
        group_percent = round(population*percentage1(2));
        groups_populations = [group_percent,population-group_percent];
        
        % NumberlineP: sampling range: 1D numberline of grid positions, to initialise agent positions 
        % For Loop: populate grid world with group 1 then group 2 agents.
        %generates no repeating random numbers from possible grid position
        %range defined in "numberline". 
        %Group1 selects positions first from the sampling range, these
        %values are then removed allowing group2 to randomly sample from
        %the remaining positions. 
        
        numberline = 1:num_range;
        for i = 1:length(groups_populations)
           ran = randperm(length(numberline), groups_populations(i));
           ran_pos = numberline(ran);
           gridworld([ran_pos]) = groups(i);
           numberline([ran]) = [];
        end
        
        %pads simulation space with NaN, to allow the kernel/mask
        %(spot light of agents surrounding neighbours) to sweep
        %onto the edges of the matrix that defines the simulation space
        gridworld = padarray(gridworld,[1,1],NaN);

        for time = 1:timesteps  
            % Function: Satisfaction 
            % Calculates agents neighbours, agents satisfaction and whether
            % the agent wants to relocate.
            % Function: MoveAgents
            % Move The agents according to the relocation list
            % see which locations are available for relocation
            [world, relocation_list, mean_segregation,percent_satisfied] = satisfaction(gridworld, tolerance(j));
            [new_world_state] = MoveAgents(relocation_list,world);
            gridworld = new_world_state;

            %Helper function for drawing and saving figure images.
            endtrial = PlotDrawSave(gridworld,tolerance(j),mean_segregation,percent_satisfied,...
                      time, groups_populations, timesteps, multiplots,...
                      stopdrawing,absolute_path,saveplots);     
        if isequal(endtrial, 1), break, end 
                   
        end 
        
    end 


end 
