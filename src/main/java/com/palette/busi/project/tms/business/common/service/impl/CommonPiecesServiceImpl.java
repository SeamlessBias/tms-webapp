package com.palette.busi.project.tms.business.common.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.palette.busi.project.tms.business.common.service.CommonPiecesService;
import com.palette.busi.project.tms.business.common.vo.ComPiecesRefUpdateVo;
import com.palette.busi.project.tms.business.common.vo.ComPiecesStatusUpdateVo;
import com.palette.busi.project.tms.common.base.BaseServiceImpl;
import com.palette.busi.project.tms.common.util.BigDecimalUtils;
import com.palette.busi.project.tms.common.util.ThrowExp;
import com.palette.busi.project.tms.core.dao.TmPiecesCurrentDao;
import com.palette.busi.project.tms.core.dao.TmPiecesHistoryDao;
import com.palette.busi.project.tms.core.dao.TmPiecesRefDao;
import com.palette.busi.project.tms.core.entity.TmPieces;
import com.palette.busi.project.tms.core.entity.TmPiecesAction;
import com.palette.busi.project.tms.core.entity.TmPiecesCurrent;
import com.palette.busi.project.tms.core.entity.TmPiecesHistory;
import com.palette.busi.project.tms.core.entity.TmPiecesRef;

@Service
public class CommonPiecesServiceImpl extends BaseServiceImpl implements CommonPiecesService {

	@Autowired
	private TmPiecesCurrentDao tmPiecesCurrentDao;
	@Autowired
	private TmPiecesHistoryDao tmPiecesHistoryDao;
	@Autowired
	private TmPiecesRefDao tmPiecesRefDao;
	
	@Override
	public void updatePiecesStatus(ComPiecesStatusUpdateVo updateVo) {
		
		// Query pieces action
		TmPiecesAction tmPiecesActionQuery = new TmPiecesAction();
		tmPiecesActionQuery.setActionCode(updateVo.getActionCode());
		tmPiecesActionQuery.setIsActivity(1);
		TmPiecesAction tmPiecesAction = querier.selectTmPiecesActionOneByRecord(tmPiecesActionQuery);
		ThrowExp.isNull(tmPiecesAction, "操作失败。更新包裹状态，查询不到TmPiecesAction数据");
		boolean isLogisticsAction = tmPiecesAction.getIsLogistics() == 1;
		
		// Insert or Update pieces current
		if(isLogisticsAction) {
			TmPiecesCurrent tmPiecesCurrentQuery = new TmPiecesCurrent();
			tmPiecesCurrentQuery.setTmPiecesId(updateVo.getTmPiecesId());
			TmPiecesCurrent tmPiecesCurrent = querier.selectTmPiecesCurrentOneByRecord(tmPiecesCurrentQuery);
			if(tmPiecesCurrent == null) {
				tmPiecesCurrent = new TmPiecesCurrent();
				
				tmPiecesCurrent.setTmPiecesId(updateVo.getTmPiecesId());
				tmPiecesCurrent.setPiecesNo(updateVo.getPiecesNo());
			}
			
			tmPiecesCurrent.setActionCode(updateVo.getActionCode());
			tmPiecesCurrent.setMemo(updateVo.getMemo());
			tmPiecesCurrent.setActionUserName(updateVo.getActionUserName());
			tmPiecesCurrent.setActionDateTime(updateVo.getActionDateTime());
			
			tmPiecesCurrentDao.saveTmPiecesCurrent(tmPiecesCurrent, updateVo.getUserName(), updateVo.getControllerId());
		}
		
		// Insert pieces history
		TmPiecesHistory tmPiecesHistory = new TmPiecesHistory();
		tmPiecesHistory.setPiecesNo(updateVo.getPiecesNo());
		tmPiecesHistory.setTmPiecesId(updateVo.getTmPiecesId());
		tmPiecesHistory.setActionCode(updateVo.getActionCode());
		tmPiecesHistory.setMemo(updateVo.getMemo());
		tmPiecesHistory.setActionUserName(updateVo.getActionUserName());
		tmPiecesHistory.setActionDateTime(updateVo.getActionDateTime());
		
		tmPiecesHistoryDao.insertTmPiecesHistory(tmPiecesHistory, updateVo.getUserName(), updateVo.getControllerId());
	}
	
	@Override
	public void updatePiecesRefInfo(ComPiecesRefUpdateVo updateVo) {
		
		TmPiecesRef tmPiecesRefQuery = new TmPiecesRef();
		tmPiecesRefQuery.setTmPiecesId(updateVo.getTmPiecesId());
		tmPiecesRefQuery.setRefType(updateVo.getRefType());
		TmPiecesRef tmPiecesRef = querier.selectTmPiecesRefOneByRecord(tmPiecesRefQuery);
		if(tmPiecesRef == null) {
			tmPiecesRef = new TmPiecesRef();
			
			tmPiecesRef.setTmPiecesId(updateVo.getTmPiecesId());
			tmPiecesRef.setPiecesNo(updateVo.getPiecesNo());
			tmPiecesRef.setRefType(updateVo.getRefType());
		}
		
		tmPiecesRef.setRefCode(updateVo.getRefCode());
		tmPiecesRef.setRefId(updateVo.getRefId());
		
		tmPiecesRefDao.saveTmPiecesRef(tmPiecesRef, updateVo.getUserName(), updateVo.getControllerId());
	}

	@Override
	public BigDecimal getPiecesChargedWeight(TmPieces tmPieces) {
		
		BigDecimal result = BigDecimalUtils.getBiggerOrEqual(tmPieces.getActualWeight(), tmPieces.getVolumeWeight());
		return result;
	}
}
