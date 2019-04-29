function drawgridworld(gridworld,tolerance,mean_segregation,percent_satisfied,...
                       time, population_size, stopdrawing)
                   
b = imshow(gridworld, [], 'InitialMagnification','fit');
set(b,'AlphaData',~isnan(gridworld))

txta = 'World State Tolerance =';
txtb = 'Mean percentage Segregation =';
txtc = 'Agent Sat =';
txtd = 'Time Step =';
txte = '//Population sizes =';
txtf = ":";
txt2 =char(strcat(txta, string(tolerance)));
txt3 = char(strcat(txtb, string(mean_segregation)));
txt4 = char(strcat(txtc, string(percent_satisfied)));
txt5 = char(strcat(txtd, string(time), txte, string(population_size(1)),txtf, string(population_size(2))));
dim = [.3 .02 .20 .08];
title(txt2)
xlabel(txt3, 'FontWeight', 'bold')
ylabel(txt4, 'FontWeight', 'bold')
anno = annotation('textbox',dim,'String',txt5,'FitBoxToText','on', 'LineStyle', 'none');
pause(0.15)
if ~isequal(stopdrawing, 1)
    delete(anno)
end 
end 