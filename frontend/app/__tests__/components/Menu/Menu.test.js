import {render, fireEvent, act} from '@testing-library/react'
import {describe, test, expect, jest} from '@jest/globals';

jest.mock("../../../src/components/Menu/videoes.svg", () => () => <h1>videoes</h1>)

import Menu from "../../../src/components/Menu"

describe(Menu, () => {

    const providers = [...Array(10).keys()].map(n => {return {
        provider: "provider" + n, 
        favicon: "favicon" + n,
        sorts: [...Array(n).keys()].reduce((acc, cur) => Object.assign(acc, {[`test${cur}`]: `test${cur}URL`}), {})
    }})

    test("correct providers count", () => {
        const menu = render(<Menu providers={providers} showMenu={true} />)
        expect(menu.getByRole("list").childElementCount).toEqual(11)
    })

    test("correct sorts count", async () => {
        const menu = render(<Menu providers={providers} showMenu={true} />)
        const pros = menu.getAllByRole('listitem')

        for(let i=0; i<pros.length-1; ++i) {
            await act( async () => {
                fireEvent.click(pros[i])
            });

            let lists = menu.queryAllByRole("list")
            expect(lists.length).toEqual(2)
            expect(lists[1].childElementCount).toEqual(i)
        }
    })

    test("should hide Menu and change page", async () => {
        const setPage = jest.fn()
        const setShowMenu = jest.fn()
        const menu = render(<Menu providers={providers} showMenu={true} setShowMenu={setShowMenu} setPage={setPage} />)
        const pros = menu.getAllByRole('listitem')

        for(let i=0; i<pros.length-1; ++i) {

            const pro = pros[i]
            await act( async () => {
                fireEvent.click(pro)
            });

            const lists = menu.getAllByRole("list")
            expect(lists.length).toEqual(2)
            const sorts =lists[1]
            for(let j=0; sorts && j<sorts.length; ++j) {
                const sort = sorts[j]
                await act( async () => {
                    fireEvent.click(sort)
                });
                expect(setPage.mock.lastCall[0]).toBe(`test${j}URL`)
                expect(setShowMenu.mock.lastCall[0]).toBe(false)
            }

        }

    })

})