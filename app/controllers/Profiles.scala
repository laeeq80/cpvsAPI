package controllers

import models.Profile
import models.Profile._
import play.api.libs.json.Json
import play.api.mvc._

object Profiles extends Controller {

  def profileByLigandId(lId: String) = Action {
    val allProfiles = Profile.findProfileByLigandId(lId)
    if ((allProfiles == null) || (allProfiles.length < 1)) {
      NotFound("Profile Not Available in Database, but you can use our prediction service at /profile/prediction/lId/:lid/rName/:rName/rPdbCode/:rPdbCode")
    } else
      Ok(Json.obj("result" -> allProfiles))
  }

  def dockAndSave(lId: String, rName: String, rPdbCode: String) = Action {
    val profile = Profile.computeAndSaveScore(lId, rName, rPdbCode)

    Ok(Json.obj("Score" -> profile))
  }
  
  def predict(lId: String, rName: String, rPdbCode: String) = Action {
    val profile = Profile.predictAndSave(lId, rName, rPdbCode)

    Ok(Json.obj("Prediction" -> profile))
  }

  def welcome() = Action {
    Ok("CPVS API is Up and Running" + "\n" + "Please Use /profiles end point for details and /receptors end point for list of receptors and their pdbCodes")
  }

}
