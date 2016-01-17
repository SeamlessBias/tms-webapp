package com.palette.busi.project.tms.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.ResultType;

import com.palette.busi.project.tms.core.entity.TmPieces;
import com.palette.busi.project.tms.core.dao.TmPiecesSqlProvider;
import com.palette.busi.project.tms.core.base.BaseMybatisMapper;

public interface TmPiecesIntf extends BaseMybatisMapper {


	@Select("SELECT  TM_PIECES_ID  AS tmPiecesId, SEQ  AS seq, MEMBER_CODE  AS memberCode, PIECES_NO  AS piecesNo, LOGISTICS_NO  AS logisticsNo, ACTUAL_WEIGHT  AS actualWeight, VOLUME_WEIGHT  AS volumeWeight, LENGTH  AS length, WIDTH  AS width, HEIGHT  AS height, CHECK_DATE  AS checkDate, WAREHOUSE_CODE  AS warehouseCode, TM_CONSIGNMENT_ID  AS tmConsignmentId, CONSIGNMENT_NO  AS consignmentNo, UNIT_NO  AS unitNo, TM_UNIT_ID  AS tmUnitId, DELIVERY_DATE  AS deliveryDate, MEMO  AS memo, IS_BIND_DELIVERY  AS isBindDelivery, RECORD_VERSION  AS recordVersion, CREATE_USER_CODE  AS createUserCode, CREATE_DATE_TIME  AS createDateTime, UPDATE_USER_CODE  AS updateUserCode, UPDATE_DATE_TIME  AS updateDateTime FROM tm_pieces WHERE tm_pieces_id=#{tmPiecesId}")
	public TmPieces selectTmPiecesById(@Param("tmPiecesId") int tmPiecesId);
	
	@Select("SELECT  TM_PIECES_ID  AS tmPiecesId, SEQ  AS seq, MEMBER_CODE  AS memberCode, PIECES_NO  AS piecesNo, LOGISTICS_NO  AS logisticsNo, ACTUAL_WEIGHT  AS actualWeight, VOLUME_WEIGHT  AS volumeWeight, LENGTH  AS length, WIDTH  AS width, HEIGHT  AS height, CHECK_DATE  AS checkDate, WAREHOUSE_CODE  AS warehouseCode, TM_CONSIGNMENT_ID  AS tmConsignmentId, CONSIGNMENT_NO  AS consignmentNo, UNIT_NO  AS unitNo, TM_UNIT_ID  AS tmUnitId, DELIVERY_DATE  AS deliveryDate, MEMO  AS memo, IS_BIND_DELIVERY  AS isBindDelivery, RECORD_VERSION  AS recordVersion, CREATE_USER_CODE  AS createUserCode, CREATE_DATE_TIME  AS createDateTime, UPDATE_USER_CODE  AS updateUserCode, UPDATE_DATE_TIME  AS updateDateTime FROM tm_pieces")
	public List<TmPieces> selectAllTmPieces();
	
	@Insert("insert into tm_pieces ( TM_PIECES_ID, SEQ, MEMBER_CODE, PIECES_NO, LOGISTICS_NO, ACTUAL_WEIGHT, VOLUME_WEIGHT, LENGTH, WIDTH, HEIGHT, CHECK_DATE, WAREHOUSE_CODE, TM_CONSIGNMENT_ID, CONSIGNMENT_NO, UNIT_NO, TM_UNIT_ID, DELIVERY_DATE, MEMO, IS_BIND_DELIVERY, RECORD_VERSION, CREATE_USER_CODE, CREATE_DATE_TIME, UPDATE_USER_CODE, UPDATE_DATE_TIME ) values (#{tmPiecesId},#{seq},#{memberCode},#{piecesNo},#{logisticsNo},#{actualWeight},#{volumeWeight},#{length},#{width},#{height},#{checkDate},#{warehouseCode},#{tmConsignmentId},#{consignmentNo},#{unitNo},#{tmUnitId},#{deliveryDate},#{memo},#{isBindDelivery},#{recordVersion},#{createUserCode},#{createDateTime},#{updateUserCode},#{updateDateTime})")
	public int insertTmPieces(TmPieces tmPieces);

	@UpdateProvider(type=TmPiecesSqlProvider.class, method="update")
	public int updateTmPieces(TmPieces tmPieces);
	
	@Delete("delete from tm_pieces where tm_pieces_id = #{tmPiecesId}")
	public int deleteTmPieces(@Param("tmPiecesId") int tmPiecesId);

    @SelectProvider(type=TmPiecesSqlProvider.class, method="selectAllByRecord")
    @ResultType(TmPieces.class)
    public List<TmPieces> selectAllByRecord(TmPieces record);
    
}