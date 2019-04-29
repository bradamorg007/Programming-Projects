% Dynamics 

% Tolerance 
% Survival Needs
% Resources
% Motivation to Work or probability of working
% Work status (working Not Working)
% Overall satisfaction (includes: resources,neighbour satisfaction, survival needs)
% Neighbours net tolerance 
% Region
% Number Relocations made on neighbour disatisfaction
% Number Relocations based on Resources
close all
clear all

%MODEL PARAMTERS
grid_size = [27];
groups = [1, -1];
%tolerance = [0.8];
tol_max = 70; %percentage
tol_min = 70;
percentage = [65,50];
resources = [20,10,5];
agent_resmax = 320;
survival_max = 100;
res_pool_max = 800;
res_threshold = 100;
maxpay = 30;
relocation_cost = 55;

timesteps =120;
NobodyAround_Rule = 0;
Avoid_total_emptyspace = 'no';
frame_rate = 0.05;


divisor = round(max(res_pool_max)/length(resources));
 stopdrawing = 0;
        
        %define empty grid world 
        gridworld = zeros(grid_size, grid_size);
        resourceworld = zeros(grid_size, grid_size);
        Id_grid = zeros(grid_size, grid_size);
        grid_num_range = grid_size^2;
        res_num_range = grid_size^2;
        
        %calculate population percentages relative to empty space and per
        %group.
        percentage1 = percentage/100;
        population = round(grid_num_range*percentage1(1));
        group_percent = round(population*percentage1(2));
        groups_populations = [group_percent,population-group_percent];
        
       AGENT_DYNAMICS = zeros(16, population);
        grid_numberline = 1:grid_num_range;
        res_numberline = 1:res_num_range;
        
        %Generate resource Space
      for j = 1:length(resources)
         len1 = round(length(res_numberline)*(resources(j)/100));
         ran2 = randperm(length(res_numberline), len1);
         ran_pos2 = res_numberline(ran2);
         res_pool_min = res_pool_max-divisor;
         resourceworld([ran_pos2]) = randi([res_pool_min, res_pool_max],1,len1);
         res_pool_max = res_pool_min;
         res_numberline([ran2]) = [];
      end 
      %Agents locations: Groups view & Individuals: Main Cellular
      %Automatata plot
        for i = 1:length(groups_populations)
           ran1 = randperm(length(grid_numberline), groups_populations(i));
           ran_pos1 = grid_numberline(ran1);
           gridworld([ran_pos1]) = groups(i);
           Id_grid([ran_pos1]) = ran_pos1;
           if i <= 1
           AGENT_DYNAMICS(1,1:groups_populations(i)) = ran_pos1;
           AGENT_DYNAMICS(2,1:groups_populations(i)) = groups(i);
           else 
            AGENT_DYNAMICS(1,groups_populations(i-1)+1:population) = ran_pos1;  
            AGENT_DYNAMICS(2,groups_populations(i-1)+1:population) = groups(i);
           end 
           grid_numberline([ran1]) = [];
        end
        
        [rows, cols] = size(AGENT_DYNAMICS);
        
        AGENT_DYNAMICS(3, :) = randi([tol_min, tol_max], 1, cols)/100; %Tolerances
        AGENT_DYNAMICS(5, :) = randi(survival_max,1, cols); %Survival Needs
        AGENT_DYNAMICS(6, :) = randi(agent_resmax,1, cols); %Resources
        AGENT_DYNAMICS(7, :) = 0; %WORK = 1 or no Work = 0
        AGENT_DYNAMICS(8, :) = randi(res_threshold, 1, cols);%percentage threshold that is consider low resources.
        
        gridworld = padarray(gridworld,[1,1],NaN);
        resourceworld = padarray(resourceworld,[1,1],NaN);
        Id_grid = padarray(Id_grid,[1,1],NaN);

        % Main Simulation Loop.
        for time = 1:timesteps  
            %All Agents survival needs reduce each timestep
            AGENT_DYNAMICS(5, :) = AGENT_DYNAMICS(5, :) - 2;
            
          [AGENT_DYNAMICS1,mean_segregation, percent_satisfie] = Agent_mechanics(gridworld, resourceworld, Id_grid, AGENT_DYNAMICS, relocation_cost, NobodyAround_Rule, Avoid_total_emptyspace);
          [new_gridworld, new_resourceworld,new_Id_grid,NEW_AGENT_DYNAMICS, death] = AgentChoices(AGENT_DYNAMICS1, gridworld, resourceworld, Id_grid, maxpay, relocation_cost);
          Mean_seg_store(time) = mean_segregation; 
          death_store(time) = death;
%           [m, d] = find(NEW_AGENT_DYNAMICS(12, :) == 1);
%           for n = 1:length(d)
%               if n == 1
%               NEW_AGENT_DYNAMICS(:, d(n)) = [];
%               elseif n > 1
%                   NEW_AGENT_DYNAMICS(:, d(n)-n) = [];
%               end 
%           end 
          gridworld = new_gridworld;
          resourceworld = new_resourceworld;
          Id_grid = new_Id_grid;
          AGENT_DYNAMICS = NEW_AGENT_DYNAMICS;
          
          if isempty(AGENT_DYNAMICS)
              break
          end 
          
        txt3 = strcat('Agent World Space, max iterations:', string(timesteps), 'Tolerance:', string(tol_max));
       txt4 = strcat('Agents: Segregation = ', string(mean_segregation));
       
       if isequal(time, 1)
         fig13 = figure(13);
           s = heatmap(gridworld);
           colormap(s, bone)
       title(txt3)
       xlabel(txt4)
       
       pause(frame_rate)
       
        
       fig3 = figure(34);
      % set(fig3,'units','normalized','outerposition',[0 0 1 1])
       q = heatmap(resourceworld);
       colormap(q,hot)
       title('Resource Distribution')
          pause(frame_rate)
       end 
           
      fig1 = figure(1);
%        set(fig1,'units','normalized','outerposition',[0 0 1 1])
%       
       a = heatmap(gridworld);
       colormap(a, bone)
       title(txt3)
       xlabel(txt4)
       
       pause(frame_rate)
       
%        fig2 = figure(2);
%        set(fig2,'units','normalized','outerposition',[0 0 1 1])
%        b = heatmap(Id_grid);
%        %colormap(a, bone)
%        title('Agent World: Individual Locations')
%          pause(0.5)
%        
       fig3 = figure(3);
      % set(fig3,'units','normalized','outerposition',[0 0 1 1])
       c = heatmap(resourceworld);
       colormap(c,hot)
       title('Resource Distribution')
          pause(frame_rate)
       end
       txt = strcat('Agent Dynamics, max iterations:', string(timesteps), 'Tolerance:', string(tol_max));
       txt2 = strcat('Agents: Segregation = ', string(mean_segregation));
        figure(4)
        bar(NEW_AGENT_DYNAMICS(13, :))
        ylabel('Relocations:Poverty(Survival based behaviour)')
        xlabel(txt2)
          title(txt)
        grid on
        figure(5)
        bar(NEW_AGENT_DYNAMICS(14, :))
        ylabel('Relocations:Wealth(Minority Avoidance based behaviour)')
        xlabel(txt2)
          title(txt)
        grid on
        figure(6)
        bar(NEW_AGENT_DYNAMICS(15, :))
        ylabel('Remains:Poverty(Survival based behaviour)')
        xlabel(txt2)
          title(txt)
        grid on
        figure(7)
        bar(NEW_AGENT_DYNAMICS(16, :))
        ylabel('Remains:Wealth(Profit/explotation based behaviour)')
        xlabel(txt2)
          title(txt)
        grid on
     
        grid on 
        txt = strcat('Agent Dynamics, max iterations:', string(timesteps), 'Tolerance:', string(tol_max));
        title(txt)
     
        xlabel(txt2)
        ylabel('Agent Parameter Values')
       
        figure(8)
        bar(NEW_AGENT_DYNAMICS(6, :))
        ylabel('Agents Final Wealth(Resources)')
        xlabel(txt2)
          title(txt)
        grid on
        
        
        figure(9)
        bar(NEW_AGENT_DYNAMICS(5, :))
        ylabel('Agents Final Survival level(0 = regional Eviction)')
        xlabel(txt2)
          title(txt)
        grid on
        
        figure(10)
        plot(Mean_seg_store)
         xlabel(txt2)
         ylabel('Level of Regional segregation')
          title('Change in segregation over time')
          grid on
        figure(11)
        plot(death_store)
         xlabel(txt2)
         ylabel('Level of Regional Agent Death/Regional Eviction')
          title('Change in Agent Death/Regional Eviction')
          
          grid on
        



