package com.innocent.growingdeveloperclickergame.project2

enum class Company(
    val korName: String,
    val projects: Array<Project>) {

    // 레벨 순서대로 오름차순.
    GOOGLE(
        "구글",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    MICROSOFT(
        "마이크로소프트",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    APPLE(
        "애플",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    NAVER(
        "네이버",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    KAKAO(
        "카카오",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    LINE(
        "라인",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    COUPANG(
        "쿠팡",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    BAEMIN(
        "배달의민족",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    DAANGN(
        "당근마켓",
        arrayOf(Project.A, Project.B, Project.C)
    ),
    TOSS(
        "토스",
        arrayOf(Project.A, Project.B, Project.C)
    );

    fun hasProject(project: Project): Boolean {
        return projects.contains(project)
    }

    companion object {

        fun getNextLevelCompany(company: Company?): Company? {
            val values = values()
            val currentIdx = values.indexOf(company)
            return if (values.size - 1 > currentIdx) values[currentIdx] else null
        }
    }
}