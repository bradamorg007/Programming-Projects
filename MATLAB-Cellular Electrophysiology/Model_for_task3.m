%===simulation time===
TimeSeries = 35; %in milliseconds
SampleRate=.01;
timestep=0:SampleRate:TimeSeries;


%===specif5 the external current I===
changeTimes = [0]; %in milliseconds
startCurrentLevels = [0];%Change this to see effect of different currents on voltage (Suggested values: 3, 20, 50, 1000)
currentLevels  = [1.9];

% level1 = 0.615;
level0 = -3.2;
level1 = -0.324943; %1.015; %-0.325

% level2 = 2;
% level3 = 2.5;
% level4 = 4;
% level5 = 4.5;

%Set externally applied current across time
IterationWindow(1:500) = level0; IterationWindow(501:1500) = level0 ; IterationWindow(1501:2500) = level1; 

%System Constants 
gbar_K=36;
gbar_Na=120;
g_L=.3;
E_K = -12;
E_Na=115;
E_L=10.6;
C=1;


%Calculate the inital State of the Cell membrane 
V=0; %Baseline voltage
An_gate = .01 * ( (10-V) / (exp((10-V)/10)-1) ); %Equation 12
Bn_gate = .125*exp(-V/80); %Equation 13
Am_gate = .1*( (25-V) / (exp((25-V)/10)-1) ); %Equation 20
Bm_gate = 4*exp(-V/18); %Equation 21
Ah_gate = .07*exp(-V/20); %Equation 23
Bh_gate = 1/(exp((30-V)/10)+1); %Equation 24

n(1) = An_gate/(An_gate+Bn_gate); %Equation 9
m(1) = Am_gate/(Am_gate+Bm_gate); %Equation 18
h(1) = Ah_gate/(Ah_gate+Bh_gate); %Equation 18


for i=1:numel(timestep)-1 %Compute coefficients, currents, and derivates at each time step
    
  %Calculate the Rate Constants across the time series -1 for inital state
  %calc
    An_gate(i) = .01 * ( (10-V(i)) / (exp((10-V(i))/10)-1) );
    Bn_gate(i) = .125*exp(-V(i)/80);
    
    Am_gate(i) = .1*( (25-V(i)) / (exp((25-V(i))/10)-1) );
    Bm_gate(i) = 4*exp(-V(i)/18);
    
    Ah_gate(i) = .07*exp(-V(i)/20);
    Bh_gate(i) = 1/(exp((30-V(i))/10)+1);
    
    
    %---calculate the currents---%
    Ion_Na = (m(i)^3) * gbar_Na * h(i) * (V(i)-E_Na); %Equations 3 and 14
    Ion_K = (n(i)^4) * gbar_K * (V(i)-E_K); %Equations 4 and 6
    Ion_L = g_L *(V(i)-E_L); %Equation 5
    main_Equ = - I_K - I_Na - I_L +  I(i) ; 
    
    
    %---calculate the derivatives using Euler first order approximation---%
    V(i+1) = V(i) + SampleRate*main_Equ/C;
    n(i+1) = n(i) + deltaT*(alpha_n(i) *(1-n(i)) - beta_n(i) * n(i)); %Equation 7
    m(i+1) = m(i) + deltaT*(alpha_m(i) *(1-m(i)) - beta_m(i) * m(i)); %Equation 15
    h(i+1) = h(i) + deltaT*(alpha_h(i) *(1-h(i)) - beta_h(i) * h(i)); %Equation 16

end


V = V-65; %Set resting potential to -70mv

%DATA ANALYSIS ============================================================

%===Voltage Graph===%
figure; plot(t,V,'LineWidth',1.5)
hold on
plot(t, I, 'r','LineWidth',2);
legend({'voltage'}, 'Injected Current (mA)')
ylabel('Voltage (mv)')
xlabel('time (ms)')
title('Voltage over Time in Simulated Neuron')

set(gca, 'XTick', [5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100])
set(gca, 'YTick', [-80, -75, -70, -65, -60, -55, -50, -45, -40, -35, -30, -25, -20, -15, -10, -5, 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60])
 
 grid on 


% ===Conductance Graph===%
figure
p1 = plot(t,gbar_K*n.^4,'LineWidth',2);
hold on
p2 = plot(t,gbar_Na*(m.^3).*h,'r','LineWidth',2);

legend([p1, p2], 'Conductance for Potassium', 'Conductance for Sodium')
ylabel('Conductance')
xlabel('time (ms)')
title('Conductance for Potassium and Sodium Ions in Simulated Neuron')

figure;

%Injected Current Graph 
plot(t, I, 'r','LineWidth',2)

set(gca, 'XTick', [5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100])
set(gca, 'YTick', [0,0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 3.0])
% -3, -2.5, -2.0, -1.5, -1.0, -0.5,
legend({'Current'})
ylabel('Injected Current(mA)')
xlabel('time (ms)')
title('Injected Current over Time in Simulated Neuron')
grid on 

figure; 

hold on 
plot(t, n, 'LineWidth', 1.5)
plot(t, m, 'LineWidth', 1.5)
plot(t, h, 'LineWidth', 1.5)

%Gating Variable Graph 
title('Na & K Ion Channel: Gating Variables')
xlabel('Time(ms)')
ylabel('Probability')
legend('n', 'm', 'h')
hold off
set(gca, 'XTick', [5, 10, 15, 20, 25, 30, 35,])

grid on 