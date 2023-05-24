#!/bin/bash
kill  $(cat ./pid.file)
rm ./pid.file