package com.innocent.growingdeveloperclickergame.project2

object ProjectDC {

    private var projectsResult: ProjectsResult? = null

    fun init(company: Company?, projectGradeMap: Map<Project, ProjectResultGrade?>?) {
        this.projectsResult = if (company != null) ProjectsResult(company, projectGradeMap) else null
    }

    fun getProjectGradeMap(): Map<Project, ProjectResultGrade?> {
        return projectsResult?.getProjectGradeMap() ?: mapOf()
    }

    fun completeProject(project: Project, grade: ProjectResultGrade) {
        if (projectsResult == null)
            return
        projectsResult!!.completeProject(project, grade)
        if (projectsResult!!.isCompleteProjectsAll())
            init(Company.getNextLevelCompany(projectsResult!!.company), null)
    }
}