import {render, fireEvent, act} from '@testing-library/react'
import {describe, test, expect, jest} from '@jest/globals';

jest.mock('../../../src/service/RecordService', () => { return {
    addDrama: function (_providerName, _dramaName) { return Promise.resolve("add"); },
    removeDrama: function (_providerName, _dramaName) { return Promise.resolve("remove") }
}})
jest.mock('../../../src/components/Box/save.svg', () => () => (<div>Save</div>))
jest.mock('../../../src/components/Box/saved.svg', () => () => (<div>Saved</div>))
import Box from '../../../src/components/Box/Box'


describe(Box, () => {
    const drama =  { 
        providerName: "test",
        name: "test" ,
        imageUrl: "testUrl", 
        videoUrl: "testUrl", 
    }

    test("renders correctly", () => {
        const box = render(<Box drama={drama} record={[]}></Box>)
        expect(box.asFragment()).toMatchSnapshot()
    })

    test("should be Save state", () => {
        const box = render(<Box drama={drama} record={[]}></Box>)
        expect(box.queryByRole('button', {name: "Save"})).not.toBeNull()
    })

    test("should be Saved state", () => {
        const box = render(<Box drama={drama} record={[drama]}></Box>)
        expect(box.queryByRole('button', {name: "Saved"})).not.toBeNull()
    })

    test("should change Save to Saved state", async () => {
        const box = render(<Box drama={drama} record={[]}></Box>)
        const btn = box.queryByRole('button', {name: "Save"})

        await act( async () => {
            fireEvent.click(btn)
        });
        expect(box.queryByRole('button', {name: "Saved"})).not.toBeNull()
    })


})