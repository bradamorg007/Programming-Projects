
function [session] = MEG_outlierRejection(session)


    data_all = session.data.preProcessed.data;
    %Grab planar gradiometers
    cfg=[];
    cfg.channel='MEGGRAD';
    data_planar=ft_selectdata(cfg,data_all);

    % data browser window
    cfg = [];
    cfg.viewmode='vertical';
    cfg.continuous='no';
    ft_databrowser(cfg, data_planar);

    % summary stats window for removing outliers
    cfg=[];
    cfg.method  = 'summary';
    cfg.layout  = 'neuromag306planar.lay';
    data_all_rjv_planar  = ft_rejectvisual(cfg, data_planar);

    % filter out any rejected trials thatw as selected in the summary stats
    % browser
    trl_rej=find(ismember(data_planar.sampleinfo(:,1),setdiff(data_planar.sampleinfo(:,1),data_all_rjv_planar.sampleinfo(:,1))));
    trl_keep=(1:size(data_all.trial,2));
    trl_keep([trl_rej]) = [];
    chan_rej=setdiff(data_planar.label,data_all_rjv_planar.label);

    % PITFALL Here possibly
    % Filter out any rejected gradometer channels
    chan_keep=data_planar.label;
    chan_keep(find(ismember(data_planar.label(:,1),chan_rej(:,1))))=[];

    % create a new data structure with the filter trials and channels.
    cfg=[];
    cfg.trials=trl_keep;
    cfg.channel=chan_keep;
    data_all_sumrj=ft_selectdata(cfg,data_all);

    %TEST THIS = REMOVE EVENT DATArelative trials that were removed
    session.data.events(:, trl_rej) = [];
    session.data.outliers = data_all_sumrj;
    

end



