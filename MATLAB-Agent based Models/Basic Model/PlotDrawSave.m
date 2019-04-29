function endtrial = PlotDrawSave(gridworld,tolerance,mean_segregation,percent_satisfied,...
                  time, groups_populations, timesteps, multiplots,...
                  stopdrawing, absolute_path, saveplots)      
   if isequal(percent_satisfied, 100) || isequal(time, timesteps)
                
            stopdrawing = 1;
            endtrial = 1;
            
            if strcmpi(saveplots, 'yes')
               if ~strcmpi(multiplots, 'yes')
                  drawgridworld(gridworld,tolerance,mean_segregation,percent_satisfied, time, groups_populations, stopdrawing);
                  txt = char(strcat(absolute_path, "\timestepTest",string(time), ".png"));
                  saveas(gcf,txt);
                elseif strcmpi(multiplots, 'yes')
                  figure();
                  drawgridworld(gridworld,tolerance,mean_segregation,percent_satisfied, time, groups_populations, stopdrawing);
                  txt = char(strcat(absolute_path, "\Tolerance=",string(tolerance), ".png"));
                  saveas(gcf,txt);
               end 
            end 
            return
            
  elseif ~strcmpi(multiplots, 'yes') 
       drawgridworld(gridworld,tolerance,mean_segregation,percent_satisfied, time, groups_populations, stopdrawing);
          if strcmpi(saveplots, 'yes')
                txt = char(strcat(absolute_path, "\timestepTest",string(time), ".png"));
                saveas(gcf,txt);
                endtrial = 0;
              
          end 
  end 
 
  endtrial = 0;
end 