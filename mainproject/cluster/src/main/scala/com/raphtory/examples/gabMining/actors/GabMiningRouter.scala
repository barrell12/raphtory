package com.raphtory.examples.gabMining.actors

import java.text.SimpleDateFormat

import com.raphtory.core.components.Router.TraditionalRouter.Helpers.RouterSlave
import com.raphtory.core.model.communication._

class GabMiningRouter (routerId:Int,override val initialManagerCount:Int) extends RouterSlave {

  def parseRecord(record: Any): Unit = {
    val fileLine = record.asInstanceOf[String].split(";").map(_.trim)
    val sourceNode=fileLine(2).toInt
    val targetNode=fileLine(5).toInt

    val creationDate = dateToUnixTime(timestamp=fileLine(0).slice(0,19))

    //create sourceNode
    toPartitionManager(VertexAdd(routerId, creationDate, sourceNode))
    //create destinationNode
    toPartitionManager(VertexAdd(routerId, creationDate, targetNode))
    //create edge
    //if (targetNode>0)
    toPartitionManager(EdgeAdd(routerId, creationDate, sourceNode, targetNode))

  }

  def dateToUnixTime(timestamp: => String): Long = {
    //if(timestamp == null) return null;
   // println("TIME FUNC: "+ timestamp)
    //val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:mm")
    val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    //println(sdf)
    val dt = sdf.parse(timestamp)
    //println(dt)
    val epoch = dt.getTime
   // println(epoch)
    epoch

  }
}