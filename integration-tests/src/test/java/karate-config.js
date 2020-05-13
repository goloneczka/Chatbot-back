function fn() {
    return {
        user: 'admin',
        password: 'Admin123*pip-test2020',
        incorrectPassword: 'Admin123*pip+2020',
        baseUrl: "http://localhost:8081,",
        testCategory: {
            category: "'karateTestCategory'",
            isConfirmed: "'true'"
        },
        createTestCategory:{
            category: "createTestCategory",
            isConfirmed: false
        },
        testJoke: {
            joke: "'karateTestJoke'",
            category: "'karateTestCategory'",
            isConfirmed: true
        },
        createTestJoke: {
            joke: "createTestJoke",
            category: "karateTestCategory",
            isConfirmed: false
        },
        testMark: {
            mark: 5
        },
        createMarkTest: {
            mark: 4.5
        },
        dbConfig: {
            username: 'app',
            password: 'Ao4eiT2w',
            url: 'jdbc:postgresql://trainings:5432/appdb_test1',
            driverClassName: 'org.postgresql.Driver'
        }

    }
}
