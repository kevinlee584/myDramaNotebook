import {describe, test, expect, jest} from '@jest/globals';
import {render, fireEvent, act} from '@testing-library/react';

jest.mock("../../../src/components/Page/reload.svg", () => () => <h1>reload</h1>)
jest.mock("../../../src/components/Box/Box.js", () => () => <h1>Box</h1>)
import Page from "../../../src/components/Page/Page";

describe(Page, () => {
    const dramas = [...Array(21).keys()].map(n => {
        return { 
            providerName: `test${n}`,
            name: `test${n}` ,
            imageUrl: `test${n}Url`, 
            videoUrl: `test${n}Url`, 
        }
    })

    test("show correct count of boxes", async() => {
        const page = render(<Page dramas={dramas} record={[]} reload={() => {}}></Page>)
        const more_btn = page.getByRole("button", {name: "more"})

        expect(page.getAllByRole("generic").find(e => e.id == "page")).not.toBeFalsy()
        expect(more_btn).not.toBeFalsy()
        expect(page.getAllByRole("heading", {name: "Box"}).length).toBe(10)

        await act( async () => {
            fireEvent.click(more_btn)
        });

        expect(page.getAllByRole("heading", {name: "Box"}).length).toBe(20)

        await act( async () => {
            fireEvent.click(more_btn)
        });

        expect(page.getAllByRole("heading", {name: "Box"}).length).toBe(21)
        expect(page.queryByRole("button", {name: "more"})).toBeFalsy()
    })

    test("should reload", async () => {
        const reload = jest.fn()
        const page = render(<Page dramas={dramas} record={[]} reload={reload}></Page>)
        const reload_btn = page.queryByRole("button", {name: "reload"})
        expect(reload_btn).not.toBeFalsy()

        for(let i in [...Array(10).keys()])
            await act( async () => {
                fireEvent.click(reload_btn)
            });

        expect(reload.mock.calls.length).toBe(10)
    }) 


})
 