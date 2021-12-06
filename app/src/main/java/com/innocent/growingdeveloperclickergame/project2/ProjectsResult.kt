package com.innocent.growingdeveloperclickergame.project2

class ProjectsResult(
    val company: Company,
    projectGradeMap: Map<Project, ProjectResultGrade?>?
) {

    private val projectGradeMap: MutableMap<Project, ProjectResultGrade?> =
        projectGradeMap?.toMutableMap() ?: mutableMapOf()

    init {
        company.projects.forEach {
            if (this.projectGradeMap.containsKey(it))
                this.projectGradeMap[it] = null
        }
    }

    fun completeProject(project: Project, grade: ProjectResultGrade) {
        projectGradeMap[project] = grade
    }

    fun isCompleteProjectsAll(): Boolean {
        return company.projects.all { projectGradeMap.contains(it) }
    }

    fun getProjectGradeMap(): Map<Project, ProjectResultGrade?> {
        return projectGradeMap.toMap()
    }

}